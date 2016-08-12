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

import rx.Observable
import rx.android.schedulers.AndroidSchedulers.mainThread
import rx.subjects.BehaviorSubject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Clock {

  var name: String? = "Bart"

  private val format = SimpleDateFormat("h:mm:ss a", Locale.US)
  private val tzSubject: BehaviorSubject<TimeZone> = BehaviorSubject.create(
      TimeZone.getTimeZone("GMT-5"))

  val currentTime: Observable<String>
    get() = Observable.interval(0, 1, TimeUnit.SECONDS, mainThread())
        .flatMap({ tzSubject })
        .map { tz ->
          val cal = Calendar.getInstance()
          format.timeZone = tz
          format.format(cal.time)
        }


  val timeZone: Observable<TimeZone> = tzSubject

  fun setTimeZone(tzId: String) {
    tzSubject.onNext(TimeZone.getTimeZone(tzId))
  }
}
