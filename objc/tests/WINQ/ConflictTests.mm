/*
 * Tencent is pleased to support the open source community by making
 * WCDB available.
 *
 * Copyright (C) 2017 THL A29 Limited, a Tencent company.
 * All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *       https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#import "WINQTestCase.h"

@interface ConflictTests : WINQTestCase

@end

@implementation ConflictTests {
}

- (void)setUp
{
    [super setUp];
}

- (void)test_default_constructible
{
    WCDB::Conflict constructible __attribute((unused));
}

- (void)test_conflict
{
    WINQEnumAssertEqual(WCDB::Conflict::Abort, @"ON CONFLICT ABORT");
    WINQEnumAssertEqual(WCDB::Conflict::Fail, @"ON CONFLICT FAIL");
    WINQEnumAssertEqual(WCDB::Conflict::Rollback, @"ON CONFLICT ROLLBACK");
    WINQEnumAssertEqual(WCDB::Conflict::Ignore, @"ON CONFLICT IGNORE");
    WINQEnumAssertEqual(WCDB::Conflict::Replace, @"ON CONFLICT REPLACE");
}

@end
