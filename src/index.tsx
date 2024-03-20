import React from 'react'
import { StyleSheet, Text, View, NativeModules, Pressable } from 'react-native'
import { Provider } from 'react-redux'
import { createStore, applyMiddleware } from 'redux'
import rootReducer from './reducer'
import thunk from 'redux-thunk'
const store = createStore(rootReducer, applyMiddleware(thunk))

const { APPUtils } = NativeModules

const App = () => {
    return (
        <Provider store={store}>
            <View style={styles.container}>
                <Pressable
                    onPress={() => {
                        // console.log(APPUtils?.AndroidPackageName)
                        APPUtils?.jumpToPhonePage({
                            phone: '13387561236'
                        }).then((message: any) => {
                            console.log(message, '----message---')
                        })
                    }}>
                    <Text>do something......</Text>
                </Pressable>
            </View>
        </Provider>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1
    }
})

export default App
