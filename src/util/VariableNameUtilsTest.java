/*
 * Copyright (C) 2015 Seesaa Inc.
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

package util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class VariableNameUtilsTest {

    @Test
    public void testCamelToSneakCase_noUpperCase() throws Exception {
        String origin = "nouppercase";
        String actual = VariableNameUtils.camelToSneakCase(origin);

        assertThat(actual, is("nouppercase"));
    }

    @Test
    public void testCamelToSneakCase_oneUpperCase() throws Exception {
        String origin = "oneupperCase";
        String actual = VariableNameUtils.camelToSneakCase(origin);

        assertThat(actual, is("oneupper_case"));
    }

    @Test
    public void testCamelToSneakCase_threeUpperCase() throws Exception {
        String origin = "haveThreeUpperCase";
        String actual = VariableNameUtils.camelToSneakCase(origin);

        assertThat(actual, is("have_three_upper_case"));
    }

    @Test
    public void testCamelToSneakCase_startUpperCase() throws Exception {
        String origin = "StringStartWithUpperCase";
        String actual = VariableNameUtils.camelToSneakCase(origin);

        assertThat(actual, is("string_start_with_upper_case"));
    }

    @Test
    public void testCamelToSneakCase_continuousUpperCase() throws Exception {
        String origin = "continuousUPPERCase";
        String actual = VariableNameUtils.camelToSneakCase(origin);

        assertThat(actual, is("continuous_uppercase"));
    }

    @Test
    public void testCamelToSneakCase_onlyUpperCase() throws Exception {
        String origin = "ONLYUPPERCASE";
        String actual = VariableNameUtils.camelToSneakCase(origin);

        assertThat(actual, is("onlyuppercase"));
    }

    @Test
    public void testCamelToSneakCase_haveUnderScore() throws Exception {
        String origin = "have_underscore";
        String actual = VariableNameUtils.camelToSneakCase(origin);

        assertThat(actual, is("have_underscore"));
    }

    @Test
    public void testCamelToSneakCase_haveUnderScoreFrontOfUpperCase() throws Exception {
        String origin = "have_Underscore";
        String actual = VariableNameUtils.camelToSneakCase(origin);

        assertThat(actual, is("have_underscore"));
    }

    @Test
    public void testCamelToSneakCase_startWithUnderScore() throws Exception {
        String origin = "_StartWithUnderScore";
        String actual = VariableNameUtils.camelToSneakCase(origin);

        assertThat(actual, is("_start_with_under_score"));
    }

    @Test
    public void testCamelToSneakCase_continuousUnderScore() throws Exception {
        String origin = "___ThreeUnderScore";
        String actual = VariableNameUtils.camelToSneakCase(origin);

        assertThat(actual, is("___three_under_score"));
    }

}