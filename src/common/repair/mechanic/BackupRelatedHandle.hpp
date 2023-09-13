//
// Created by 陈秋文 on 2023/9/8.
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

#include "Backup.hpp"
#include "InnerHandle.hpp"

namespace WCDB {

namespace Repair {

class BackupRelatedHandle : public InnerHandle,
                            public BackupSharedDelegate,
                            public BackupExclusiveDelegate {
public:
    BackupRelatedHandle();
    ~BackupRelatedHandle() override;

#pragma mark - Backup
public:
    void setBackupPath(const UnsafeStringView &path) override final;
    const StringView &getBackupPath() const override final;
    const Error &getBackupError() const override final;

    bool acquireBackupSharedLock() override final;
    bool releaseBackupSharedLock() override final;

    bool acquireBackupExclusiveLock() override final;
    bool releaseBackupExclusiveLock() override final;

    void finishBackup() override final;
};

} // namespace Repair

} // namespace WCDB
