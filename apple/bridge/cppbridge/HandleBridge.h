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

#pragma once
#include <WCDB/ObjectBridge.h>

#include <WCDB/ErrorBridge.h>
#include <WCDB/HandleStatementBridge.h>
#include <WCDB/WinqBridge.h>

WCDB_EXTERN_C_BEGIN

WCDBDefineCPPBridgedType(CPPHandle)

CPPError WCDBHandleGetError(CPPHandle handle);
bool WCDBHandleCheckValid(CPPHandle handle);

CPPHandleStatement WCDBHandlePrepare(CPPHandle handle, CPPStatement statement);
bool WCDBHandleExcute(CPPHandle handle, CPPStatement statement);

int WCDBHandleGetChange(CPPHandle handle);
int WCDBHandleGetTotalChange(CPPHandle handle);
signed long long WCDBHandleGetLastInsertedRowID(CPPHandle handle);

bool WCDBHandleIsReadOnly(CPPHandle handle);
bool WCDBHandleIsInTransaction(CPPHandle handle);

WCDB_EXTERN_C_END
