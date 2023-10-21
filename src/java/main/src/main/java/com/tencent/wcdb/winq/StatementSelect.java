// Created by chenqiuwen on 2023/4/1.
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

package com.tencent.wcdb.winq;

import com.tencent.wcdb.base.CppObject;

import java.util.Arrays;

public class StatementSelect extends Statement implements TableOrSubqueryConvertible {
    @Override
    protected int getType() {
        return CPPType.SelectSTMT;
    }
    public StatementSelect() {
        cppObj = createCppObj();
    }
    private static native long createCppObj();

    public StatementSelect with(CommonTableExpression expression) {
        return with(new CommonTableExpression[]{expression});
    }

    public StatementSelect with(CommonTableExpression... expressions) {
        if(expressions == null || expressions.length == 0) {
            return this;
        }
        long[] cppExps = new long[expressions.length];
        for(int i = 0; i < expressions.length; i++) {
            cppExps[i] = CppObject.get(expressions[i]);
        }
        configWith(cppObj, cppExps);
        return this;
    }

    public StatementSelect withRecursive(CommonTableExpression expression) {
        return withRecursive(new CommonTableExpression[]{expression});
    }

    public StatementSelect withRecursive(CommonTableExpression... expressions) {
        if(expressions == null || expressions.length == 0) {
            return this;
        }
        long[] cppExps = new long[expressions.length];
        for(int i = 0; i < expressions.length; i++) {
            cppExps[i] = CppObject.get(expressions[i]);
        }
        configWith(cppObj, cppExps);
        configRecursive(cppObj);
        return this;
    }

    private static native void configWith(long self, long[] expressions);

    private static native void configRecursive(long self);

    public StatementSelect select(ResultColumnConvertible resultColumn) {
        configResultColumns(cppObj, new int[]{Identifier.getCppType(resultColumn)},
                new long[]{CppObject.get(resultColumn)}, null, null);
        return this;
    }

    public StatementSelect select(ResultColumnConvertible... resultColumns) {
        if(resultColumns == null || resultColumns.length == 0) {
            return this;
        }
        int totalCount = resultColumns.length;
        int[] types = new int[totalCount];
        long[] cppObjs = new long[totalCount];
        for(int i = 0; i < totalCount; i++) {
            types[i] = Identifier.getCppType(resultColumns[i]);
            cppObjs[i] = CppObject.get(resultColumns[i]);
        }
        configResultColumns(cppObj, types, cppObjs, null, null);
        return this;
    }

    public StatementSelect select(String resultColumn) {
        configResultColumns(cppObj, new int[]{CPPType.String}, null, null,
                new String[]{resultColumn});
        return this;
    }

    public StatementSelect select(String... resultColumns) {
        if(resultColumns == null || resultColumns.length == 0) {
            return this;
        }
        int[] types = new int[resultColumns.length];
        Arrays.fill(types, CPPType.String);
        configResultColumns(cppObj, types, null, null, resultColumns);
        return this;
    }

    public StatementSelect select(Object... resultColumns) {
        if(resultColumns == null || resultColumns.length == 0) {
            return this;
        }
        int totalCount = resultColumns.length;
        int[] types = new int[totalCount];
        long[] cppObjs = new long[totalCount];
        String[] columnNames = new String[totalCount];
        int intIndex = 0;
        int stringIndex = 0;
        for(int i = 0; i < totalCount; i++) {
            Object obj = resultColumns[i];
            if(obj instanceof String){
                types[i] = CPPType.String;
                columnNames[stringIndex] = (String) obj;
                stringIndex++;
            } else if (obj instanceof ResultColumnConvertible) {
                ResultColumnConvertible resultColumn = (ResultColumnConvertible)obj;
                types[i] = Identifier.getCppType(resultColumn);
                cppObjs[intIndex] = CppObject.get(resultColumn);
                intIndex++;
            }
        }
        if(columnNames.length * 0.75 > stringIndex) {
            if(stringIndex == 0) {
                columnNames = null;
            } else {
                columnNames = Arrays.copyOf(columnNames, stringIndex);
            }
        }
        configResultColumns(cppObj, types, cppObjs, null, columnNames);
        return this;
    }

    private static native void configResultColumns(long self, int[] types, long[] columns, double[] unused, String[] columnNames);

    public StatementSelect distinct() {
        configDistinct(cppObj);
        return this;
    }

    private static native void configDistinct(long self);

    public StatementSelect from(TableOrSubqueryConvertible tableOrSubquery) {
        configTableOrSubqueries(cppObj, new int[]{Identifier.getCppType(tableOrSubquery)},
                new long[]{CppObject.get(tableOrSubquery)}, null, null);
        return this;
    }

    public StatementSelect from(TableOrSubqueryConvertible[] tableOrSubqueries) {
        if(tableOrSubqueries == null || tableOrSubqueries.length == 0) {
            return this;
        }
        int totalCount = tableOrSubqueries.length;
        int[] types = new int[totalCount];
        long[] cppObjs = new long[totalCount];
        for(int i = 0; i < totalCount; i++) {
            types[i] = Identifier.getCppType(tableOrSubqueries[i]);
            cppObjs[i] = CppObject.get(tableOrSubqueries[i]);
        }
        configTableOrSubqueries(cppObj, types, cppObjs, null, null);
        return this;
    }

    public StatementSelect from(String tableName) {
        configTableOrSubqueries(cppObj, new int[]{CPPType.String}, null, null, new String[]{tableName});
        return this;
    }

    public StatementSelect from(String... tableNames) {
        if(tableNames == null || tableNames.length == 0) {
            return this;
        }
        int[] types = new int[tableNames.length];
        Arrays.fill(types, CPPType.String);
        configTableOrSubqueries(cppObj, types, null, null, tableNames);
        return this;
    }

    public StatementSelect from(Object... tableOrSubqueries) {
        if(tableOrSubqueries == null || tableOrSubqueries.length == 0) {
            return this;
        }
        int totalCount = tableOrSubqueries.length;
        int[] types = new int[totalCount];
        long[] cppObjs = new long[totalCount];
        String[] tablNames = new String[totalCount];
        int intIndex = 0;
        int stringIndex = 0;
        for(int i = 0; i < totalCount; i++) {
            Object obj = tableOrSubqueries[i];
            if(obj instanceof String){
                types[i] = CPPType.String;
                tablNames[stringIndex] = (String) obj;
                stringIndex++;
            } else if (obj instanceof TableOrSubqueryConvertible) {
                TableOrSubqueryConvertible tableOrSubquery = (TableOrSubqueryConvertible)obj;
                types[i] = Identifier.getCppType(tableOrSubquery);
                cppObjs[intIndex] = CppObject.get(tableOrSubquery);
                intIndex++;
            }
        }
        if(tablNames.length * 0.75 > stringIndex) {
            if(stringIndex == 0) {
                tablNames = null;
            } else {
                tablNames = Arrays.copyOf(tablNames, stringIndex);
            }
        }
        configTableOrSubqueries(cppObj, types, cppObjs, null, tablNames);
        return this;
    }

    private static native void configTableOrSubqueries(long self, int[] types, long[] tableOrSubqueries, double[] unused, String[] tableNames);

    public StatementSelect where(Expression condition) {
        configCondition(cppObj, CppObject.get(condition));
        return this;
    }

    private static native void configCondition(long self, long condition);

    public StatementSelect groupBy(ExpressionConvertible expression) {
        configGroups(cppObj, new int[]{Identifier.getCppType(expression)}, new long[]{CppObject.get(expression)}, null, null);
        return this;
    }

    public StatementSelect groupBy(ExpressionConvertible... expressions) {
        if(expressions == null || expressions.length == 0) {
            return this;
        }
        int totalCount = expressions.length;
        int[] types = new int[totalCount];
        long[] cppObjs = new long[totalCount];
        for(int i = 0; i < totalCount; i++) {
            types[i] = Identifier.getCppType(expressions[i]);
            cppObjs[i] = CppObject.get(expressions[i]);
        }
        configGroups(cppObj, types, cppObjs, null, null);
        return this;
    }

    public StatementSelect groupBy(String columnName) {
        configGroups(cppObj, new int[]{CPPType.String}, null, null, new String[]{columnName});
        return this;
    }

    public StatementSelect groupBy(String... columnNames) {
        if(columnNames == null || columnNames.length == 0) {
            return this;
        }
        int[] types = new int[columnNames.length];
        Arrays.fill(types, CPPType.String);
        configGroups(cppObj, types, null, null, columnNames);
        return this;
    }

    public StatementSelect groupBy(Object... expressions) {
        if(expressions == null || expressions.length == 0) {
            return this;
        }
        int totalCount = expressions.length;
        int[] types = new int[totalCount];
        long[] cppObjs = new long[totalCount];
        String[] columnNames = new String[totalCount];
        int intIndex = 0;
        int stringIndex = 0;
        for(int i = 0; i < totalCount; i++) {
            Object obj = expressions[i];
            if(obj instanceof String){
                types[i] = CPPType.String;
                columnNames[stringIndex] = (String) obj;
                stringIndex++;
            } else if (obj instanceof ExpressionConvertible) {
                ExpressionConvertible expression = (ExpressionConvertible)obj;
                types[i] = Identifier.getCppType(expression);
                cppObjs[intIndex] = CppObject.get(expression);
                intIndex++;
            }
        }
        if(columnNames.length * 0.75 > stringIndex) {
            if(stringIndex == 0) {
                columnNames = null;
            } else {
                columnNames = Arrays.copyOf(columnNames, stringIndex);
            }
        }
        configGroups(cppObj, types, cppObjs, null, columnNames);
        return this;
    }

    private static native void configGroups(long self, int[] types, long[] exps, double[] unused, String[] columNames);

    public StatementSelect having(Expression expression) {
        configHaving(cppObj, CppObject.get(expression));
        return this;
    }

    private static native void configHaving(long self, long expression);

    public StatementSelect union() {
        configUnion(cppObj);
        return this;
    }

    private static native void configUnion(long self);

    public StatementSelect unionAll() {
        configUnionAll(cppObj);
        return this;
    }

    private static native void configUnionAll(long self);

    public StatementSelect intersect() {
        configIntersect(cppObj);
        return this;
    }

    private static native void configIntersect(long self);

    public StatementSelect except() {
        configExcept(cppObj);
        return this;
    }

    private static native void configExcept(long self);

    public StatementSelect orderBy(OrderingTerm order) {
        configOrders(cppObj, new long[]{CppObject.get(order)});
        return this;
    }

    public StatementSelect orderBy(OrderingTerm... orders) {
        if(orders == null || orders.length == 0) {
            return this;
        }
        long[] cppOrders = new long[orders.length];
        for(int i = 0; i < orders.length; i++) {
            cppOrders[i] = CppObject.get(orders[i]);
        }
        configOrders(cppObj, cppOrders);
        return this;
    }

    private static native void configOrders(long self, long[] orders);

    public StatementSelect limit(long from, long to) {
        configLimitRange(cppObj, CPPType.Int, from, CPPType.Int, to);
        return this;
    }

    public StatementSelect limit(long from, ExpressionConvertible to) {
        configLimitRange(cppObj, CPPType.Int, from, Identifier.getCppType(to), CppObject.get(to));
        return this;
    }

    public StatementSelect limit(ExpressionConvertible from, ExpressionConvertible to) {
        configLimitRange(cppObj, Identifier.getCppType(from), CppObject.get(from), Identifier.getCppType(to), CppObject.get(to));
        return this;
    }

    public StatementSelect limit(ExpressionConvertible from, long to) {
        configLimitRange(cppObj, Identifier.getCppType(from), CppObject.get(from), CPPType.Int, to);
        return this;
    }

    private static native void configLimitRange(long self, int fromType, long from, int toType, long to);

    public StatementSelect limit(long count) {
        configLimitCount(cppObj, CPPType.Int, count);
        return this;
    }

    public StatementSelect limit(ExpressionConvertible count) {
        configLimitCount(cppObj, Identifier.getCppType(count), CppObject.get(count));
        return this;
    }

    private static native void configLimitCount(long self, int type, long count);

    public StatementSelect offset(long offset) {
        configOffset(cppObj, CPPType.Int, offset);
        return this;
    }

    public StatementSelect offset(ExpressionConvertible offset) {
        configOffset(cppObj, Identifier.getCppType(offset), CppObject.get(offset));
        return this;
    }

    private static native void configOffset(long self, int type, long count);
}

