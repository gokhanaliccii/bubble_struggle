/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
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
 ******************************************************************************/

package com.galici.test.examples;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;
import com.galici.test.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class AssetExistsTest {

	@Test
	public void backgroundFileShouldReturnFailure() {
		
		assertTrue("Background file dont exist",isFileExist("../android/assets/background2.jpg"));
	}

	@Test
	public void animationPathShouldReturnOK() {

		String forwardWalkingAnim = "../android/assets/atlases/forward-walking.atlas";
		String rightWalkingAnim = "../android/assets/atlases/right-walking.atlas";
		String leftWalkingAnim = "../android/assets/atlases/left-walking.atlas";
		String arrowAnim = "../android/assets/atlases/weapon.atlas";

		/** walking animations **/
		assertTrue("Commander forward animation is exist",
				isFileExist(forwardWalkingAnim));
		assertTrue("Commander right animation is exist",
				isFileExist(rightWalkingAnim));
		assertTrue("Commander left animation is exist",
				isFileExist(leftWalkingAnim));

		/** arrow animation **/
		assertTrue("Weapon animation is exist", isFileExist(arrowAnim));
	}

	@Test
	public void soundTestShouldFail(){
		
		String timeoutSound = "../android/sounds/timeout.png";
		String startSound = "../android/sounds/start_sound.wav";
		String bulletSound = "../android/sounds/bullet_sound.mp3";
		
		assertTrue("Timeout sound is exist",isFileExist(timeoutSound));
		assertTrue("Start sound is exist",isFileExist(startSound));
		assertTrue("Bullet sound is exist",	isFileExist(bulletSound));
	}
	
	private boolean isFileExist(String path) {

		return Gdx.files.internal(path).exists();
	}
}
