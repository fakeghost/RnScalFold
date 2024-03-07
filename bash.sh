#!/bin/bash


platform=$platform # -p 平台，android或者ios
git_desc=$commit  # -m 热更新描述，若未传，则取最后一次git提交的描述
environment=$env #  -e 环境，dev or preview or release ，发布到测试环境还是正式环境
force_update=$force_update # -f true or false，是否开启强制更新
echo $force_update
while getopts "p:e:f:m:" opt; do
    case "$opt" in 
    p)
    platform=$OPTARG
    ;;
    e)
    environment=$OPTARG
    ;;
    f)
    force_update=$OPTARG
    ;;
    m)
    git_desc=$OPTARG
    # ;;
    esac
done 

work_space=$(pwd) #根路径
# 判断代码分支
current_branch=$branch
echo $current_branch
if [ $environment = "preview" ] && [ $current_branch != "preview" ]; then
  echo "提示：preview环境的包最好在preview分支打，线上包必须在release分支打"
elif [ $environment = "release" ] && [ $current_branch != "origin/release" ]; then
  echo "release环境的包需要在release分支打"
  exit 1
else
  echo "提示：dev环境的包最好在dev分支打，否则会覆盖别人的包。即将开始打包"
fi

host="xxx"
if [ $environment = "preview" ]; then 
  host="xxx"
elif [ $environment = "release" ]; then
  host="xxx"
fi

curdate="`date +%Y%m%d`"
curtime="`date +%H_%M_%S`"
echo "$platform-$environment-$curdate-$curtime"

# release才会标记
if  [ $environment = "release" ];then
  git tag "$environment_$platform_$curdate_$curtime"
  git push --tags
else
  echo "非release分支不打标签"
fi

# 拷贝config文件
bundle_path=$work_space/build/$platform
mkdir -p $bundle_path
cp -r $work_space/config/build.config.json $bundle_path/config.json

# 读取config文件
JQ_EXEC=`which jq`
FILE_PATH=$work_space/build/$platform/config.json
buz_name=$(cat $FILE_PATH | ${JQ_EXEC} .name | sed 's/\"//g')
buz_module=$(cat $FILE_PATH | ${JQ_EXEC} .module | sed 's/\"//g')
native_api_level=$(cat $FILE_PATH | ${JQ_EXEC} .nativeApiLevel.$platform | sed 's/\"//g')
md5=$(cat $FILE_PATH | ${JQ_EXEC} .version | sed 's/\"//g')
# 确认打包信息
echo "打包平台为：$platform, 描述: $git_desc, native api版本: $native_api_level，打包环境为：$env",

#打包

#安装yarn
npm install yarn --registry=http://registry.npmmirror.com -g

# 安装依赖包
yarn install
npx react-native bundle --platform $platform --dev false --entry-file index.js --bundle-output $bundle_path/index.$platform.bundle --assets-dest $bundle_path --reset-cache

# 更新config.json
md5=`md5sum $bundle_path/index.$platform.bundle | awk '{ print $1 }'`
 
system=`uname`
echo $system
if [ "$system" == "Darwin" ]; then
  # 这里是 macOS 下的代码
  sed -i "" 's/\("version": "\).*/\1'"$md5"'",/g' $bundle_path/config.json
  sed -i "" 's/\("description": "\).*/\1'"$git_desc"'"/g' $bundle_path/config.json
else
  # 这里是 Linux 下的代码
  sed -i 's/\("version": "\).*/\1'"$md5"'",/g' $bundle_path/config.json
  sed -i 's/\("description": "\).*/\1'"$git_desc"'"/g' $bundle_path/config.json
fi


# 压缩
cd $bundle_path
cd ..
zip -q -r -9 -dg $platform.zip $platform

#获取token：服务端提供的算法
timeStamp=`date +%s`
currentTimeStamp=`expr $timeStamp \* 1000`
token=`expr $currentTimeStamp + 2064119939`

# 上传文件到服务器
pwd
curl --trace-ascii -X POST $host -H "AuthToken:$token" -H "accept:*/*" -H "Content-Type:multipart/form-data" -F "file=@$platform.zip" -F "name=$buz_name" -F "module=$buz_module" -F "version=$md5" -F "platform=$platform" -F "nativeApiLevel=$native_api_level" -F "description=$git_desc" -F "enabled=true" -F "env=$environment" -F "forceUpdate=$force_update"  --compressed --insecure
 
# 删除打包残留
rm -rf $work_space/build/$platform.zip