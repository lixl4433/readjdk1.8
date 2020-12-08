/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
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
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

/**
 * Text nodes hold the non-markup, non-Entity content of
 * an Element or Attribute.
 * <P>
 * When a document is first made available to the DOM, there is only
 * one Text object for each block of adjacent plain-text. Users (ie,
 * applications) may create multiple adjacent Texts during editing --
 * see {@link org.w3c.dom.Element#normalize} for discussion.
 * <P>
 * Note that CDATASection is a subclass of Text. This is conceptually
 * valid, since they're really just two different ways of quoting
 * characters when they're written out as part of an XML stream.
 *
 * @xerces.internal
 *
 * <p>
 *  文本节点保存元素或属性的非标记,非实体内容。
 * <P>
 *  当文档首次可用于DOM时,每个相邻纯文本块只有一个Text对象。
 * 用户(即应用程序)可以在编辑期间创建多个相邻的文本 - 请参阅{@link org.w3c.dom.Element#normalize}以供讨论。
 * <P>
 *  注意CDATASection是Text的子类。这在概念上是有效的,因为当它们作为XML流的一部分被写出时,它们只是两种不同的引用字符的方式。
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public class DeferredTextImpl
    extends TextImpl
    implements DeferredNode {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = 2310613872100393425L;

    //
    // Data
    //

    /** Node index. */
    protected transient int fNodeIndex;

    //
    // Constructors
    //

    /**
     * This is the deferred constructor. Only the fNodeIndex is given here.
     * All other data, can be requested from the ownerDocument via the index.
     * <p>
     * 
     *  @ xerces.internal
     * 
     */
    DeferredTextImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
        super(ownerDocument, null);

        fNodeIndex = nodeIndex;
        needsSyncData(true);

    } // <init>(DeferredDocumentImpl,int)

    //
    // DeferredNode methods
    //

    /** Returns the node index. */
    public int getNodeIndex() {
        return fNodeIndex;
    }

    //
    // Protected methods
    //

    /** Synchronizes the underlying data. */
    protected void synchronizeData() {

        // no need for future synchronizations
        needsSyncData(false);

        // get initial text value
        DeferredDocumentImpl ownerDocument =
            (DeferredDocumentImpl) this.ownerDocument();
        data = ownerDocument.getNodeValueString(fNodeIndex);

        // NOTE: We used to normalize adjacent text node values here.
        //       This code has moved to the DeferredDocumentImpl
        //       getNodeValueString() method. -Ac

        // ignorable whitespace
        isIgnorableWhitespace(ownerDocument.getNodeExtra(fNodeIndex) == 1);

    } // synchronizeData()

} // class DeferredTextImpl
