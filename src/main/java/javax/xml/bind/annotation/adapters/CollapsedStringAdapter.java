/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.xml.bind.annotation.adapters;



/**
 * Built-in {@link XmlAdapter} to handle <tt>xs:token</tt> and its derived types.
 *
 * <p>
 * This adapter removes leading and trailing whitespaces, then truncate any
 * sequnce of tab, CR, LF, and SP by a single whitespace character ' '.
 *
 * <p>
 *  内置{@link XmlAdapter}以处理<tt> xs：token </tt>及其派生类型。
 * 
 * <p>
 *  此适配器删除前导和尾随空格,然后通过单个空格字符""截断选项卡,CR,LF和SP的任何序列。
 * 
 * 
 * @author Kohsuke Kawaguchi
 * @since JAXB 2.0
 */
public class CollapsedStringAdapter extends XmlAdapter<String,String> {
    /**
     * Removes leading and trailing whitespaces of the string
     * given as the parameter, then truncate any
     * sequnce of tab, CR, LF, and SP by a single whitespace character ' '.
     * <p>
     *  删除作为参数给定的字符串的前导和尾随空格,然后通过单个空格字符"''截断tab,CR,LF和SP的任何序列。
     * 
     */
    public String unmarshal(String text) {
        if(text==null)  return null;        // be defensive

        int len = text.length();

        // most of the texts are already in the collapsed form.
        // so look for the first whitespace in the hope that we will
        // never see it.
        int s=0;
        while(s<len) {
            if(isWhiteSpace(text.charAt(s)))
                break;
            s++;
        }
        if(s==len)
            // the input happens to be already collapsed.
            return text;

        // we now know that the input contains spaces.
        // let's sit down and do the collapsing normally.

        StringBuilder result = new StringBuilder(len /*allocate enough size to avoid re-allocation*/ );

        if(s!=0) {
            for( int i=0; i<s; i++ )
                result.append(text.charAt(i));
            result.append(' ');
        }

        boolean inStripMode = true;
        for (int i = s+1; i < len; i++) {
            char ch = text.charAt(i);
            boolean b = isWhiteSpace(ch);
            if (inStripMode && b)
                continue; // skip this character

            inStripMode = b;
            if (inStripMode)
                result.append(' ');
            else
                result.append(ch);
        }

        // remove trailing whitespaces
        len = result.length();
        if (len > 0 && result.charAt(len - 1) == ' ')
            result.setLength(len - 1);
        // whitespaces are already collapsed,
        // so all we have to do is to remove the last one character
        // if it's a whitespace.

        return result.toString();
    }

    /**
     * No-op.
     *
     * Just return the same string given as the parameter.
     * <p>
     *  if(s！= 0){for(int i = 0; i <s; i ++)result.append(text.charAt(i)); result.append(''); }}
     * 
     *  boolean inStripMode = true; for(int i = s + 1; i <len; i ++){char ch = text.charAt(i); boolean b = isWhiteSpace(ch); if(inStripMode && b)continue; //跳过此字符。
     * 
     *  inStripMode = b; if(inStripMode)result.append(''); else result.append(ch); }}
     * 
     *  // remove trailing whitespaces len = result.length(); if(len> 0 && result.charAt(len  -  1)=='')resu
     * lt.setLength(len  -  1); //空格已经折叠,//所有我们要做的是删除最后一个字符//如果它是一个空格。
     */
    public String marshal(String s) {
        return s;
    }


    /** returns true if the specified char is a white space character. */
    protected static boolean isWhiteSpace(char ch) {
        // most of the characters are non-control characters.
        // so check that first to quickly return false for most of the cases.
        if( ch>0x20 )   return false;

        // other than we have to do four comparisons.
        return ch == 0x9 || ch == 0xA || ch == 0xD || ch == 0x20;
    }
}
