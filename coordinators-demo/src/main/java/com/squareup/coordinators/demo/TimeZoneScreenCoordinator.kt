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

import android.view.View
import com.squareup.coordinators.Coordinator
import kotlinx.android.synthetic.main.timezone_screen.view.*
import rx.Subscription

class TimeZoneScreenCoordinator : Coordinator() {

  lateinit private var timezone: Subscription

  override fun attach(view: View) {
    timezone = view.model.timeZone.subscribe {
      view.timezone_id.text = null
      view.timezone_id.append(it.id)
    }

    view.timezone_save.onClick {
      timezone.unsubscribe()
      model.setTimeZone(view.timezone_id.text.toString())
      activity.screen = R.layout.hello_screen
    }
  }

  override fun detach(view: View) {
    timezone.unsubscribe()
  }
}
