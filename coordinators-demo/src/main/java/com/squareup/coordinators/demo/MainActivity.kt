/*
 * Copyright (C) 2016 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.coordinators.demo

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.transition.TransitionManager
import android.view.ViewGroup
import com.squareup.coordinators.Coordinators

class MainActivity : Activity() {

  @LayoutRes var screen: Int? = null
    set(layout: Int?) {
      field = layout
      TransitionManager.beginDelayedTransition(container)
      container.removeAllViews()
      layoutInflater.inflate(layout!!, container)
    }

  lateinit private var container: ViewGroup

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    container = findViewById(R.id.main_activity) as ViewGroup

    Coordinators.installBinder(container) { view ->
      when (screen) {
        R.layout.timezone_screen -> TimeZoneScreenCoordinator()
        R.layout.hello_screen -> ClockScreenCoordinator()
        else -> null
      }
    }

    screen = R.layout.timezone_screen
  }

  override fun onBackPressed() {
    when (screen) {
      R.layout.hello_screen -> screen = R.layout.timezone_screen
      else -> super.onBackPressed()
    }
  }
}
