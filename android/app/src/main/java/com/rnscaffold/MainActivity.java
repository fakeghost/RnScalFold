package com.rnscaffold;

import com.facebook.react.ReactActivity;
import com.rnscaffold.tools.DSUpdateTool;

public class MainActivity extends ReactActivity {

  @Override
  protected void onResume() {
    super.onResume();
//    new DSUpdateTool().getPackageInfo();
  }

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "RnScafFold";
  }
}
