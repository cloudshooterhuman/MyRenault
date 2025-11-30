/*
 * Copyright 2024 Abdellah Selassi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.renault.domaine

import com.renault.domain.models.Car

object DomainModelFactory {

    const val CAR_TITLE = "CAR_TITLE"
    const val CAR_IMAGE_URL = "CAR_IMAGE_URL"
    const val CAR_DESCRIPTION = "CAR_DESCRIPTION"


    fun getDefaultModelCar(
    ) = Car(
        title = CAR_TITLE,
        description = CAR_DESCRIPTION,
        imageUrl = CAR_IMAGE_URL,
    )
}
