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

#include <WCDB/Handle.hpp>
#include <WCDB/TokenizerConfig.hpp>
#include <WCDB/TokenizerModules.hpp>

namespace WCDB {

TokenizerConfig::TokenizerConfig(const UnsafeStringView& name_,
                                 const std::shared_ptr<TokenizerModules>& modules)
: Config(), name(name_), m_modules(modules)
{
}

bool TokenizerConfig::invoke(Handle* handle)
{
    bool succeed = true;
    const TokenizerModule* module = m_modules->get(name);
    if (module != nullptr) {
        bool exists = false;
        std::tie(succeed, exists) = handle->ft3TokenizerExists(name);
        if (succeed && !exists) {
            StatementSelect statement = StatementSelect().select(
            Expression::function("fts3_tokenizer").invoke().arguments(BindParameter::bindParameters(2)));
            if (handle->prepare(statement)) {
                handle->bindText(name, 1);
                UnsafeData address((unsigned char*) &module, sizeof(unsigned char*));
                handle->bindBLOB(address, 2);
                succeed = handle->step();
                handle->finalize();
            }
        }
    }
    return succeed;
}

} //namespace WCDB
