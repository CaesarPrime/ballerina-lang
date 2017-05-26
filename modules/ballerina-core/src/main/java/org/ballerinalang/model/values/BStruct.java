/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ballerinalang.model.values;

import org.ballerinalang.model.StructDef;
import org.ballerinalang.model.types.BType;

/**
 * The {@code BStruct} represents the value of a user defined struct in Ballerina.
 *
 * @since 1.0.0
 */
public final class BStruct implements BRefType<StructDef>, StructureType {

    private StructDef structDef;
    private BValue[] structMemBlock;
    private BStruct stackTrace;

    private long[] longFields;
    private double[] doubleFields;
    private String[] stringFields;
    private int[] intFields;
    private BRefType[] refFields;

    /**
     * Creates a struct with a single memory block.
     */
    public BStruct() {
        this(null, new BValue[0]);
    }

    /**
     * Creates a struct with the given size of memory block.
     *
     * @param structDef      {@link StructDef} who's values will be stored by this {@code BStruct}
     * @param structMemBlock Array of memory blocks to store values.
     */
    public BStruct(StructDef structDef, BValue[] structMemBlock) {
        this.structDef = structDef;
        this.structMemBlock = structMemBlock;
    }

    /**
     * Get a value from a memory location, stored inside this struct.
     *
     * @param offset Offset of the memory location
     * @return Value stored in the given memory location of this struct.
     */
    public BValue getValue(int offset) {
        return structMemBlock[offset];
    }

    /**
     * Set a value to a memory location of this struct.
     *
     * @param offset Offset of the memory location
     * @param bValue Value to be stored in the given memory location of this struct.
     */
    public void setValue(int offset, BValue bValue) {
        this.structMemBlock[offset] = bValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StructDef value() {
        return structDef;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String stringValue() {
        return null;
    }

    @Override
    public BType getType() {
        return structDef;
    }

    public BStruct getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(BStruct stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public void init(int[] fieldIndexes) {
        longFields = new long[fieldIndexes[0]];
        doubleFields = new double[fieldIndexes[1]];
        stringFields = new String[fieldIndexes[2]];
        intFields = new int[fieldIndexes[3]];
        refFields = new BRefType[fieldIndexes[4]];
    }

    @Override
    public long getIntField(int index) {
        return longFields[index];
    }

    @Override
    public void setIntField(int index, long value) {
        longFields[index] = value;
    }

    @Override
    public double getFloatField(int index) {
        return doubleFields[index];
    }

    @Override
    public void setFloatField(int index, double value) {
        doubleFields[index] = value;
    }

    @Override
    public String getStringField(int index) {
        return stringFields[index];
    }

    @Override
    public void setStringField(int index, String value) {
        stringFields[index] = value;
    }

    @Override
    public int getBooleanField(int index) {
        return intFields[index];
    }

    @Override
    public void setBooleanField(int index, int value) {
        intFields[index] = value;
    }

    @Override
    public BRefType getRefField(int index) {
        return refFields[index];
    }

    @Override
    public void setRefField(int index, BRefType value) {
        refFields[index] = value;
    }

    @Override
    public BValue copy() {
        BValue[] newStructMemBlock = new BValue[structMemBlock.length];
        for (int i = 0; i < structMemBlock.length; i++) {
            BValue value = structMemBlock[i];
            newStructMemBlock[i] = value == null ? null : value.copy();
        }
        return new BStruct(structDef, newStructMemBlock);
    }
}
