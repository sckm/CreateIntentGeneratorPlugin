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

public class VariableNameUtils {
    public static String camelToSneakCase(String name) {
        StringBuilder sb = new StringBuilder();
        int cur = 0;
        while (cur < name.length()) {
            char c = name.charAt(cur);

            if (!Character.isUpperCase(c)) {
                sb.append(c);
                cur++;
                continue;
            }

            if (sb.length() > 0 && sb.charAt(sb.length() - 1) != '_') {
                sb.append('_');
            }

            while (Character.isUpperCase(c)) {
                sb.append(c);
                cur++;
                if (cur >= name.length()) {
                    break;
                }
                c = name.charAt(cur);
            }
        }

        if (name.charAt(0) != '_' && sb.charAt(0) == '_') {
            sb.deleteCharAt(0);
        }

        return sb.toString().toLowerCase();
    }
}
