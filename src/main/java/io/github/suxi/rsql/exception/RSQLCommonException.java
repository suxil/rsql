/*
 * Copyright 2020 suxi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.suxi.rsql.exception;

/**
 * rsql 解析通用异常
 *
 * @author lu_it
 * @since V1.0
 */
public class RSQLCommonException extends RuntimeException {

    public RSQLCommonException() {

    }

    public RSQLCommonException(String message) {
        super(message);
    }

    public RSQLCommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public RSQLCommonException(Throwable cause) {
        super(cause);
    }

}
