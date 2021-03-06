/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2002-2004 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.sun.org.apache.xerces.internal.xs.AttributePSVI;
import com.sun.org.apache.xerces.internal.xs.*;

/**
 * Attribute namespace implementation; stores PSVI attribute items.
 *
 * @xerces.internal
 *
 * <p>
 *  属性命名空间实现;存储PSVI属性项
 * 
 * @xercesinternal
 * 
 * 
 * @author Sandy Gao, IBM
 *
 */
public class PSVIAttrNSImpl extends AttrNSImpl implements AttributePSVI {

    /** Serialization version. */
    static final long serialVersionUID = -3241738699421018889L;

    /**
     * Construct an attribute node.
     * <p>
     *  构造属性节点
     * 
     */
    public PSVIAttrNSImpl(CoreDocumentImpl ownerDocument, String namespaceURI,
                          String qualifiedName, String localName) {
        super(ownerDocument, namespaceURI, qualifiedName, localName);
    }

    /**
     * Construct an attribute node.
     * <p>
     *  构造属性节点
     * 
     */
    public PSVIAttrNSImpl(CoreDocumentImpl ownerDocument, String namespaceURI,
                          String qualifiedName) {
        super(ownerDocument, namespaceURI, qualifiedName);
    }

    /** attribute declaration */
    protected XSAttributeDeclaration fDeclaration = null;

    /** type of attribute, simpleType */
    protected XSTypeDefinition fTypeDecl = null;

    /** If this attribute was explicitly given a
    /* <p>
    /* 
     * value in the original document, this is true; otherwise, it is false  */
    protected boolean fSpecified = true;

    /** schema normalized value property */
    protected String fNormalizedValue = null;

    /** schema actual value */
    protected Object fActualValue = null;

    /** schema actual value type */
    protected short fActualValueType = XSConstants.UNAVAILABLE_DT;

    /** actual value types if the value is a list */
    protected ShortList fItemValueTypes = null;

    /** member type definition against which attribute was validated */
    protected XSSimpleTypeDefinition fMemberType = null;

    /** validation attempted: none, partial, full */
    protected short fValidationAttempted = AttributePSVI.VALIDATION_NONE;

    /** validity: valid, invalid, unknown */
    protected short fValidity = AttributePSVI.VALIDITY_NOTKNOWN;

    /** error codes */
    protected StringList fErrorCodes = null;

    /** validation context: could be QName or XPath expression*/
    protected String fValidationContext = null;

    //
    // AttributePSVI methods
    //

    /**
     * [schema default]
     *
     * <p>
     *  [schema default]
     * 
     * 
     * @return The canonical lexical representation of the declaration's {value constraint} value.
     * @see <a href="http://www.w3.org/TR/xmlschema-1/#e-schema_default>XML Schema Part 1: Structures [schema default]</a>
     */
    public String getSchemaDefault() {
        return fDeclaration == null ? null : fDeclaration.getConstraintValue();
    }

    /**
     * [schema normalized value]
     *
     *
     * <p>
     *  [模式归一化值]
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xmlschema-1/#e-schema_normalized_value>XML Schema Part 1: Structures [schema normalized value]</a>
     * @return the normalized value of this item after validation
     */
    public String getSchemaNormalizedValue() {
        return fNormalizedValue;
    }

    /**
     * [schema specified]
     * <p>
     *  [schema specified]
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xmlschema-1/#e-schema_specified">XML Schema Part 1: Structures [schema specified]</a>
     * @return false value was specified in schema, true value comes from the infoset
     */
    public boolean getIsSchemaSpecified() {
        return fSpecified;
    }


    /**
     * Determines the extent to which the document has been validated
     *
     * <p>
     *  确定文档已验证的范围
     * 
     * 
     * @return return the [validation attempted] property. The possible values are
     *         NO_VALIDATION, PARTIAL_VALIDATION and FULL_VALIDATION
     */
    public short getValidationAttempted() {
        return fValidationAttempted;
    }

    /**
     * Determine the validity of the node with respect
     * to the validation being attempted
     *
     * <p>
     *  确定节点相对于正在尝试的验证的有效性
     * 
     * 
     * @return return the [validity] property. Possible values are:
     *         UNKNOWN_VALIDITY, INVALID_VALIDITY, VALID_VALIDITY
     */
    public short getValidity() {
        return fValidity;
    }

    /**
     * A list of error codes generated from validation attempts.
     * Need to find all the possible subclause reports that need reporting
     *
     * <p>
     *  验证尝试生成的错误代码列表需要查找需要报告的所有可能的子条款报告
     * 
     * 
     * @return list of error codes
     */
    public StringList getErrorCodes() {
        return fErrorCodes;
    }

    // This is the only information we can provide in a pipeline.
    public String getValidationContext() {
        return fValidationContext;
    }

    /**
     * An item isomorphic to the type definition used to validate this element.
     *
     * <p>
     *  与用于验证此元素的类型定义同构的项
     * 
     * 
     * @return  a type declaration
     */
    public XSTypeDefinition getTypeDefinition() {
        return fTypeDecl;
    }

    /**
     * If and only if that type definition is a simple type definition
     * with {variety} union, or a complex type definition whose {content type}
     * is a simple thype definition with {variety} union, then an item isomorphic
     * to that member of the union's {member type definitions} which actually
     * validated the element item's normalized value.
     *
     * <p>
     * 如果且仅当该类型定义是一个具有{variety} union的简单类型定义,或者一个复杂类型定义,其{content type}是一个具有{variety} union的简单thype定义,那么一个项目
     * 同构于该成员的{成员类型定义},它实际上验证了元素项的归一化值。
     * 
     * 
     * @return  a simple type declaration
     */
    public XSSimpleTypeDefinition getMemberTypeDefinition() {
        return fMemberType;
    }

    /**
     * An item isomorphic to the attribute declaration used to validate
     * this attribute.
     *
     * <p>
     *  与用于验证此属性的属性声明同构的项目
     * 
     * 
     * @return  an attribute declaration
     */
    public XSAttributeDeclaration getAttributeDeclaration() {
        return fDeclaration;
    }

    /**
     * Copy PSVI properties from another psvi item.
     *
     * <p>
     *  从另一个psvi项复制PSVI属性
     * 
     * 
     * @param attr  the source of attribute PSVI items
     */
    public void setPSVI(AttributePSVI attr) {
        this.fDeclaration = attr.getAttributeDeclaration();
        this.fValidationContext = attr.getValidationContext();
        this.fValidity = attr.getValidity();
        this.fValidationAttempted = attr.getValidationAttempted();
        this.fErrorCodes = attr.getErrorCodes();
        this.fNormalizedValue = attr.getSchemaNormalizedValue();
        this.fActualValue = attr.getActualNormalizedValue();
        this.fActualValueType = attr.getActualNormalizedValueType();
        this.fItemValueTypes = attr.getItemValueTypes();
        this.fTypeDecl = attr.getTypeDefinition();
        this.fMemberType = attr.getMemberTypeDefinition();
        this.fSpecified = attr.getIsSchemaSpecified();
    }

    /* (non-Javadoc)
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xs.ItemPSVI#getActualNormalizedValue()
     */
    public Object getActualNormalizedValue() {
        return this.fActualValue;
    }

    /* (non-Javadoc)
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xs.ItemPSVI#getActualNormalizedValueType()
     */
    public short getActualNormalizedValueType() {
        return this.fActualValueType;
    }

    /* (non-Javadoc)
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xs.ItemPSVI#getItemValueTypes()
     */
    public ShortList getItemValueTypes() {
        return this.fItemValueTypes;
    }

    // REVISIT: Forbid serialization of PSVI DOM until
    // we support object serialization of grammars -- mrglavas

    private void writeObject(ObjectOutputStream out)
        throws IOException {
        throw new NotSerializableException(getClass().getName());
    }

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {
        throw new NotSerializableException(getClass().getName());
    }
}
