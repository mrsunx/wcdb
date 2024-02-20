// Created by chenqiuwen on 2023/5/2.
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
package com.tencent.wcdb.chaincall;

import com.tencent.wcdb.base.Value;
import com.tencent.wcdb.base.WCDBException;
import com.tencent.wcdb.core.Handle;
import com.tencent.wcdb.core.PreparedStatement;
import com.tencent.wcdb.orm.Field;
import com.tencent.wcdb.orm.TableBinding;
import com.tencent.wcdb.winq.Expression;
import com.tencent.wcdb.winq.ExpressionConvertible;
import com.tencent.wcdb.winq.OrderingTerm;
import com.tencent.wcdb.winq.StatementUpdate;

public class Update<T> extends ChainCall<StatementUpdate> {
    private Field<T>[] fields = null;
    private T object = null;
    private Value[] row = null;

    public Update(Handle handle, boolean needChanges, boolean autoInvalidateHandle) {
        super(handle, needChanges, autoInvalidateHandle);
        statement = new StatementUpdate();
    }

    /**
     * WINQ interface for SQL.
     * @param table The name of the table to update content.
     * @return this.
     */
    public Update<T> table(String table) {
        statement.update(table);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param fields The fields used to specify the columns where data is updated.
     * @return this.
     */
    public Update<T> set(Field<T>... fields) {
        this.fields = fields;
        statement.setColumnsToBindParameters(fields);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param object The object to be updated into table.
     * @return this.
     */
    public Update<T> toObject(T object) {
        this.object = object;
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param value Value to be updated into table.
     * @return this.
     */
    public Update<T> toValue(Value value) {
        if(value == null) {
            return toRow(new Value[]{new Value()});
        }
        return toRow(new Value[]{value});
    }

    /**
     * WINQ interface for SQL.
     * @param value Byte value to be updated into table.
     * @return this.
     */
    public Update<T> toValue(byte value) {
        return toRow(new Value[]{new Value(value)});
    }

    /**
     * WINQ interface for SQL.
     * @param value Short value to be updated into table.
     * @return this.
     */
    public Update<T> toValue(short value) {
        return toRow(new Value[]{new Value(value)});
    }

    /**
     * WINQ interface for SQL.
     * @param value Int value to be updated into table.
     * @return this.
     */
    public Update<T> toValue(int value) {
        return toRow(new Value[]{new Value(value)});
    }

    /**
     * WINQ interface for SQL.
     * @param value Long value to be updated into table.
     * @return this.
     */
    public Update<T> toValue(long value) {
        return toRow(new Value[]{new Value(value)});
    }

    /**
     * WINQ interface for SQL.
     * @param value String value to be updated into table.
     * @return this.
     */
    public Update<T> toValue(String value) {

        return toRow(new Value[]{new Value(value)});
    }

    /**
     * WINQ interface for SQL.
     * @param value Byte[] value to be updated into table.
     * @return this.
     */
    public Update<T> toValue(byte[] value) {
        return toRow(new Value[]{new Value(value)});
    }

    /**
     * WINQ interface for SQL.
     * @param value Float value to be updated into table.
     * @return this.
     */
    public Update<T> toValue(float value) {
        return toRow(new Value[]{new Value(value)});
    }

    /**
     * WINQ interface for SQL.
     * @param value Double value to be updated into table.
     * @return this.
     */
    public Update<T> toValue(double value) {
        return toRow(new Value[]{new Value(value)});
    }

    /**
     * WINQ interface for SQL.
     * @param row The row to be updated into table.
     * @return this.
     */
    public Update<T> toRow(Value... row) {
        this.row = row;
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param condition condition.
     * @return this.
     */
    public Update<T> where(Expression condition) {
        statement.where(condition);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param order order term.
     * @return this.
     */
    public Update<T> orderBy(OrderingTerm order) {
        statement.orderBy(order);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param orders Order list.
     * @return this.
     */
    public Update<T> orderBy(OrderingTerm... orders) {
        statement.orderBy(orders);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param count limit count.
     * @return this.
     */
    public Update<T> limit(short count) {
        statement.limit(count);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param count limit count.
     * @return this.
     */
    public Update<T> limit(int count) {
        statement.limit(count);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param count limit count.
     * @return this.
     */
    public Update<T> limit(long count) {
        statement.limit(count);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param count limit expression.
     * @return this.
     */
    public Update<T> limit(ExpressionConvertible count) {
        statement.limit(count);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param offset offset number.
     * @return this.
     */
    public Update<T> offset(short offset) {
        statement.offset(offset);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param offset offset number.
     * @return this.
     */
    public Update<T> offset(int offset) {
        statement.offset(offset);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param offset offset number.
     * @return this.
     */
    public Update<T> offset(long offset) {
        statement.offset(offset);
        return this;
    }

    /**
     * WINQ interface for SQL.
     * @param offset offset expression.
     * @return this.
     */
    public Update<T> offset(ExpressionConvertible offset) {
        statement.offset(offset);
        return this;
    }

    /**
     * Execute the update statement.
     * @return this.
     * @throws WCDBException if any error occurs.
     */
    public Update<T> execute() throws WCDBException {
        assert object != null || row != null;
        TableBinding<T> binding = Field.getBinding(fields);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = handle.preparedWithMainStatement(statement);
            if(object != null) {
                preparedStatement.bindObject(object, fields);
            }else if(row != null) {
                preparedStatement.bindRow(row);
            }
            preparedStatement.step();
            updateChanges();
        } finally {
            if(preparedStatement != null){
                preparedStatement.finalizeStatement();
            }
            invalidateHandle();
        }
        return this;
    }
}
