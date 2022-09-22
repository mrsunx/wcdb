//
// Created by qiuwenchen on 2022/4/1.
//

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

#include <WCDB/CoreConst.h>
#include <WCDB/ErrorBridge.h>
#include <WCDB/ErrorProne.hpp>
#include <WCDB/Notifier.hpp>
#include <WCDB/ObjectBridge.hpp>

WCDBDefineMultiArgumentSwiftClosureBridgedType(
WCDBErrorInfoEnumerator, void, const char*, WCDBErrorValueType, long, double, const char*)

long WCDBErrorGetCode(CPPError error)
{
    WCDBGetObjectOrReturnValue(error, WCDB::Error, cppError, INT_MAX);
    return WCDB::Error::c2rc(cppError->code());
}

long WCDBErrorGetLevel(CPPError error)
{
    WCDBGetObjectOrReturnValue(error, WCDB::Error, cppError, (int) WCDB::Error::Level::Error);
    return (int) cppError->level;
}

const char* WCDBErrorGetMsg(CPPError obj)
{
    WCDBGetObjectOrReturnValue(obj, WCDB::Error, cppError, "invalid error obj");
    if (cppError->getMessage().length() == 0) {
        return cppError->getMessage().data();
    }
    WCTAssert(cppError->getMessage().length() > 0 && cppError->getMessage().data() != nullptr);
    if (cppError->getMessage().length() == 0 || cppError->getMessage().data() == nullptr) {
        return "no error msg";
    }
    return cppError->getMessage().data();
}

void WCDBErrorEnumerateAllInfo(CPPError error, SwiftClosure* _Nonnull callback)
{
    WCDBErrorInfoEnumerator enumerator
    = WCDBCreateSwiftBridgedClosure(WCDBErrorInfoEnumerator, callback);
    WCTAssert(WCDBGetSwiftClosure(enumerator) != nullptr);
    if (!WCDBGetSwiftClosure(enumerator)) {
        return;
    }
    WCDBGetObjectOrReturn(error, WCDB::Error, cppError);
    for (const auto& info : cppError->infos) {
        switch (info.second.underlyingType()) {
        case WCDB::Error::InfoValue::UnderlyingType::String:
            WCDBSwiftClosureCallWithMultiArgument(enumerator,
                                                  info.first.data(),
                                                  WCDBErrorValueTypeString,
                                                  0,
                                                  0,
                                                  info.second.stringValue().data());
            break;
        case WCDB::Error::InfoValue::UnderlyingType::Integer:
            WCDBSwiftClosureCallWithMultiArgument(enumerator,
                                                  info.first.data(),
                                                  WCDBErrorValueTypeInterger,
                                                  info.second.integerValue(),
                                                  0,
                                                  nullptr);
            break;
        case WCDB::Error::InfoValue::UnderlyingType::Float:
            WCDBSwiftClosureCallWithMultiArgument(enumerator,
                                                  info.first.data(),
                                                  WCDBErrorValueTypeFloat,
                                                  0,
                                                  info.second.floatValue(),
                                                  nullptr);
            break;
        default:
            break;
        }
    }
}

void WCDBErrorReport(WCDBErrorLevel level, long code, const char* content, const char* _Nullable path, long tag)
{
    WCDB::Error::Level cppLevel;
    switch (level) {
    case WCDBErrorLevel_Ignore:
        cppLevel = WCDB::Error::Level::Ignore;
        break;
    case WCDBErrorLevel_Debug:
        cppLevel = WCDB::Error::Level::Debug;
        break;
    case WCDBErrorLevel_Warming:
        cppLevel = WCDB::Error::Level::Warning;
        break;
    case WCDBErrorLevel_Notice:
        cppLevel = WCDB::Error::Level::Notice;
        break;
    case WCDBErrorLevel_Error:
        cppLevel = WCDB::Error::Level::Error;
        break;
    case WCDBErrorLevel_Fatal:
        cppLevel = WCDB::Error::Level::Fatal;
        break;
    }
    WCDB::Error error(WCDB::Error::rc2c((int) code), cppLevel, content);
    error.infos.insert_or_assign(WCDB::ErrorStringKeySource, WCDB::ErrorSourceSwift);
    if (path != nullptr) {
        error.infos.insert_or_assign(WCDB::ErrorStringKeyPath, path);
    }
    if (tag != 0) {
        error.infos.insert_or_assign(WCDB::ErrorIntKeyTag, tag);
    }
    WCDB::Notifier::shared().notify(error);
}
