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

#include <WCDB/Assertion.hpp>
#include <WCDB/SQL.hpp>

namespace WCDB {

SQL::SQL()
{
}

SQL::SQL(const SQL& sql)
: m_syntax(sql.m_syntax), m_description(sql.m_description)
{
}

SQL::SQL(SQL&& sql)
: m_syntax(std::move(sql.m_syntax)), m_description(std::move(sql.m_description))
{
}

SQL::SQL(const Shadow<Syntax::Identifier>& syntax) : m_syntax(syntax)
{
}

SQL::SQL(Shadow<Syntax::Identifier>&& syntax) : m_syntax(std::move(syntax))
{
}

SQL::SQL(std::unique_ptr<Syntax::Identifier>&& underlying)
: m_syntax(std::move(underlying))
{
}

SQL& SQL::operator=(const SQL& other)
{
    m_syntax = other.m_syntax;
    m_description = other.m_description;
    return *this;
}

SQL& SQL::operator=(SQL&& other)
{
    m_syntax = std::move(other.m_syntax);
    m_description = std::move(other.m_description);
    return *this;
}

SQL::~SQL()
{
}

SQL::Type SQL::getType() const
{
    return m_syntax->getType();
}

void SQL::iterate(const Iterator& iterator)
{
    return m_syntax->iterate(iterator);
}

const StringView& SQL::getDescription() const
{
    if (!m_description.has_value()) {
        if (m_syntax != nullptr) {
            m_description = m_syntax->getDescription();
        } else {
            m_description = StringView();
        }
    }
    return m_description.value();
}

Syntax::Identifier& SQL::syntax()
{
    if (m_description.has_value()) {
        m_description = std::nullopt;
    }
    return *m_syntax.get();
}

const Syntax::Identifier& SQL::syntax() const
{
    return *m_syntax.get();
}

} // namespace WCDB
