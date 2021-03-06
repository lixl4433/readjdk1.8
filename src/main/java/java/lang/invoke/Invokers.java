/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.invoke;

import java.lang.reflect.Array;
import java.util.Arrays;

import static java.lang.invoke.MethodHandleStatics.*;
import static java.lang.invoke.MethodHandleNatives.Constants.*;
import static java.lang.invoke.MethodHandles.Lookup.IMPL_LOOKUP;
import static java.lang.invoke.LambdaForm.*;

/**
 * Construction and caching of often-used invokers.
 * <p>
 *  常用的调用者的构造和缓存。
 * 
 * 
 * @author jrose
 */
class Invokers {
    // exact type (sans leading taget MH) for the outgoing call
    private final MethodType targetType;

    // Cached adapter information:
    private final @Stable MethodHandle[] invokers = new MethodHandle[INV_LIMIT];
    // Indexes into invokers:
    static final int
            INV_EXACT          =  0,  // MethodHandles.exactInvoker
            INV_GENERIC        =  1,  // MethodHandles.invoker (generic invocation)
            INV_BASIC          =  2,  // MethodHandles.basicInvoker
            INV_LIMIT          =  3;

    /** Compute and cache information common to all collecting adapters
     *  that implement members of the erasure-family of the given erased type.
     * <p>
     *  其实现给定擦除类型的擦除族的成员。
     * 
     */
    /*non-public*/ Invokers(MethodType targetType) {
        this.targetType = targetType;
    }

    /* <p>
    /*  this.targetType = targetType; }}
    /* 
    /* 
    /*non-public*/ MethodHandle exactInvoker() {
        MethodHandle invoker = cachedInvoker(INV_EXACT);
        if (invoker != null)  return invoker;
        invoker = makeExactOrGeneralInvoker(true);
        return setCachedInvoker(INV_EXACT, invoker);
    }

    /* <p>
    /*  MethodHandle invoker = cachedInvoker(INV_EXACT); if(invoker！= null)return invoker; invoker = makeExa
    /* ctOrGeneralInvoker(true); return setCachedInvoker(INV_EXACT,invoker); }}。
    /* 
    /* 
    /*non-public*/ MethodHandle genericInvoker() {
        MethodHandle invoker = cachedInvoker(INV_GENERIC);
        if (invoker != null)  return invoker;
        invoker = makeExactOrGeneralInvoker(false);
        return setCachedInvoker(INV_GENERIC, invoker);
    }

    /* <p>
    /*  MethodHandle invoker = cachedInvoker(INV_GENERIC); if(invoker！= null)return invoker; invoker = makeE
    /* xactOrGeneralInvoker(false); return setCachedInvoker(INV_GENERIC,invoker); }}。
    /* 
    /* 
    /*non-public*/ MethodHandle basicInvoker() {
        MethodHandle invoker = cachedInvoker(INV_BASIC);
        if (invoker != null)  return invoker;
        MethodType basicType = targetType.basicType();
        if (basicType != targetType) {
            // double cache; not used significantly
            return setCachedInvoker(INV_BASIC, basicType.invokers().basicInvoker());
        }
        invoker = basicType.form().cachedMethodHandle(MethodTypeForm.MH_BASIC_INV);
        if (invoker == null) {
            MemberName method = invokeBasicMethod(basicType);
            invoker = DirectMethodHandle.make(method);
            assert(checkInvoker(invoker));
            invoker = basicType.form().setCachedMethodHandle(MethodTypeForm.MH_BASIC_INV, invoker);
        }
        return setCachedInvoker(INV_BASIC, invoker);
    }

    private MethodHandle cachedInvoker(int idx) {
        return invokers[idx];
    }

    private synchronized MethodHandle setCachedInvoker(int idx, final MethodHandle invoker) {
        // Simulate a CAS, to avoid racy duplication of results.
        MethodHandle prev = invokers[idx];
        if (prev != null)  return prev;
        return invokers[idx] = invoker;
    }

    private MethodHandle makeExactOrGeneralInvoker(boolean isExact) {
        MethodType mtype = targetType;
        MethodType invokerType = mtype.invokerType();
        int which = (isExact ? MethodTypeForm.LF_EX_INVOKER : MethodTypeForm.LF_GEN_INVOKER);
        LambdaForm lform = invokeHandleForm(mtype, false, which);
        MethodHandle invoker = BoundMethodHandle.bindSingle(invokerType, lform, mtype);
        String whichName = (isExact ? "invokeExact" : "invoke");
        invoker = invoker.withInternalMemberName(MemberName.makeMethodHandleInvoke(whichName, mtype), false);
        assert(checkInvoker(invoker));
        maybeCompileToBytecode(invoker);
        return invoker;
    }

    /* <p>
    /*  MethodHandle invoker = cachedInvoker(INV_BASIC); if(invoker！= null)return invoker; MethodType basicT
    /* ype = targetType.basicType(); if(basicType！= targetType){// double cache;未使用显着返回setCachedInvoker(INV_BASIC,basicType.invokers()。
    /* basicInvoker()); } invoker = basicType.form()。
    /* cachedMethodHandle(MethodTypeForm.MH_BASIC_INV); if(invoker == null){MemberName method = invokeBasicMethod(basicType); invoker = DirectMethodHandle.make(method); assert(checkInvoker(invoker)); invoker = basicType.form()。
    /* basicInvoker()); } invoker = basicType.form()。
    /* setCachedMethodHandle(MethodTypeForm.MH_BASIC_INV,invoker); } return setCachedInvoker(INV_BASIC,invok
    /* er); }}。
    /* basicInvoker()); } invoker = basicType.form()。
    /* 
    /*  private MethodHandle cachedInvoker(int idx){return invokers [idx]; }}
    /* 
    /* private synchronized MethodHandle setCachedInvoker(int idx,final MethodHandle invoker){//模拟CAS,以避免结果的重复。
    /*  MethodHandle prev = invokers [idx]; if(prev！= null)return prev; return invokers [idx] = invoker; }}。
    /* 
    /*  private MethodHandle makeExactOrGeneralInvoker(boolean isExact){MethodType mtype = targetType; MethodType invokerType = mtype.invokerType(); int which =(isExact?MethodTypeForm.LF_EX_INVOKER：MethodTypeForm.LF_GEN_INVOKER); LambdaForm lform = invokeHandleForm(mtype,false,which); MethodHandle invoker = BoundMethodHandle.bindSingle(invokerType,lform,mtype); String whichName =(isExact?"invokeExact"："invoke"); invoker = invoker.withInternalMemberName(MemberName.makeMethodHandleInvoke(whichName,mtype),false); assert(checkInvoker(invoker)); maybeCompileToBytecode(invoker); return invoker; }
    /* }。
    /* 
    /* 
    /** If the target type seems to be common enough, eagerly compile the invoker to bytecodes. */
    private void maybeCompileToBytecode(MethodHandle invoker) {
        final int EAGER_COMPILE_ARITY_LIMIT = 10;
        if (targetType == targetType.erase() &&
            targetType.parameterCount() < EAGER_COMPILE_ARITY_LIMIT) {
            invoker.form.compileToBytecode();
        }
    }

    // This next one is called from LambdaForm.NamedFunction.<init>.
    /*non-public*/ static MemberName invokeBasicMethod(MethodType basicType) {
        assert(basicType == basicType.basicType());
        try {
            //Lookup.findVirtual(MethodHandle.class, name, type);
            return IMPL_LOOKUP.resolveOrFail(REF_invokeVirtual, MethodHandle.class, "invokeBasic", basicType);
        } catch (ReflectiveOperationException ex) {
            throw newInternalError("JVM cannot find invoker for "+basicType, ex);
        }
    }

    private boolean checkInvoker(MethodHandle invoker) {
        assert(targetType.invokerType().equals(invoker.type()))
                : java.util.Arrays.asList(targetType, targetType.invokerType(), invoker);
        assert(invoker.internalMemberName() == null ||
               invoker.internalMemberName().getMethodType().equals(targetType));
        assert(!invoker.isVarargsCollector());
        return true;
    }

    /**
     * Find or create an invoker which passes unchanged a given number of arguments
     * and spreads the rest from a trailing array argument.
     * The invoker target type is the post-spread type {@code (TYPEOF(uarg*), TYPEOF(sarg*))=>RT}.
     * All the {@code sarg}s must have a common type {@code C}.  (If there are none, {@code Object} is assumed.}
     * <p>
     *  assert(basicType == basicType.basicType()); try {//Lookup.findVirtual(MethodHandle.class,name,type); return IMPL_LOOKUP.resolveOrFail(REF_invokeVirtual,MethodHandle.class,"invokeBasic",basicType); }
     *  catch(ReflectiveOperationException ex){throw newInternalError("JVM can not find invoker for"+ basicType,ex); }
     * }。
     * 
     * private boolean checkInvoker(MethodHandle invoker){assert(targetType.invokerType()。
     * equals(invoker.type()))：java.util.Arrays.asList(targetType,targetType.invokerType(),invoker); assert(
     * invoker.internalMemberName()== null || invoker.internalMemberName()。
     * private boolean checkInvoker(MethodHandle invoker){assert(targetType.invokerType()。getMethodType()。
     * equals(targetType)); assert(！invoker.isVarargsCollector()); return true; }}。
     * 
     *  / **查找或创建一个调用者,它传递给定数量的参数,并从后面的数组参数中传播其余的。调用者目标类型是后扩展类型{@code(TYPEOF(uarg *),TYPEOF(sarg *))=> RT}。
     * 所有{@code sarg}必须具有共同类型{@code C}。 (如果没有,则假定{@code Object}。}。
     * 
     * 
     * @param leadingArgCount the number of unchanged (non-spread) arguments
     * @return {@code invoker.invokeExact(mh, uarg*, C[]{sarg*}) := (RT)mh.invoke(uarg*, sarg*)}
     */
    /*non-public*/ MethodHandle spreadInvoker(int leadingArgCount) {
        int spreadArgCount = targetType.parameterCount() - leadingArgCount;
        MethodType postSpreadType = targetType;
        Class<?> argArrayType = impliedRestargType(postSpreadType, leadingArgCount);
        if (postSpreadType.parameterSlotCount() <= MethodType.MAX_MH_INVOKER_ARITY) {
            return genericInvoker().asSpreader(argArrayType, spreadArgCount);
        }
        // Cannot build a generic invoker here of type ginvoker.invoke(mh, a*[254]).
        // Instead, factor sinvoker.invoke(mh, a) into ainvoker.invoke(filter(mh), a)
        // where filter(mh) == mh.asSpreader(Object[], spreadArgCount)
        MethodType preSpreadType = postSpreadType
            .replaceParameterTypes(leadingArgCount, postSpreadType.parameterCount(), argArrayType);
        MethodHandle arrayInvoker = MethodHandles.invoker(preSpreadType);
        MethodHandle makeSpreader = MethodHandles.insertArguments(Lazy.MH_asSpreader, 1, argArrayType, spreadArgCount);
        return MethodHandles.filterArgument(arrayInvoker, 0, makeSpreader);
    }

    private static Class<?> impliedRestargType(MethodType restargType, int fromPos) {
        if (restargType.isGeneric())  return Object[].class;  // can be nothing else
        int maxPos = restargType.parameterCount();
        if (fromPos >= maxPos)  return Object[].class;  // reasonable default
        Class<?> argType = restargType.parameterType(fromPos);
        for (int i = fromPos+1; i < maxPos; i++) {
            if (argType != restargType.parameterType(i))
                throw newIllegalArgumentException("need homogeneous rest arguments", restargType);
        }
        if (argType == Object.class)  return Object[].class;
        return Array.newInstance(argType, 0).getClass();
    }

    public String toString() {
        return "Invokers"+targetType;
    }

    static MemberName methodHandleInvokeLinkerMethod(String name,
                                                     MethodType mtype,
                                                     Object[] appendixResult) {
        int which;
        switch (name) {
        case "invokeExact":  which = MethodTypeForm.LF_EX_LINKER; break;
        case "invoke":       which = MethodTypeForm.LF_GEN_LINKER; break;
        default:             throw new InternalError("not invoker: "+name);
        }
        LambdaForm lform;
        if (mtype.parameterSlotCount() <= MethodType.MAX_MH_ARITY - MH_LINKER_ARG_APPENDED) {
            lform = invokeHandleForm(mtype, false, which);
            appendixResult[0] = mtype;
        } else {
            lform = invokeHandleForm(mtype, true, which);
        }
        return lform.vmentry;
    }

    // argument count to account for trailing "appendix value" (typically the mtype)
    private static final int MH_LINKER_ARG_APPENDED = 1;

    /** Returns an adapter for invokeExact or generic invoke, as a MH or constant pool linker.
     * If !customized, caller is responsible for supplying, during adapter execution,
     * a copy of the exact mtype.  This is because the adapter might be generalized to
     * a basic type.
     * <p>
     * int spreadArgCount = targetType.parameterCount() -  leadingArgCount; MethodType postSpreadType = targ
     * etType;类<?> argArrayType = impliedRestargType(postSpreadType,leadingArgCount); if(postSpreadType.para
     * meterSlotCount()<= MethodType.MAX_MH_INVOKER_ARITY){return genericInvoker()。
     * asSpreader(argArrayType,spreadArgCount); } //无法在这里构建类型为ginvoker.invoke(mh,a * [254])的通用调用器。
     *  //相反,因子sinvoker.invoke(mh,a)into ainvoker.invoke(filter(mh),a)//其中filter(mh)== mh.asSpreader(Object 
     * [],spreadArgCount)MethodType preSpreadType = postSpreadType .replaceParameterTypes (leadingArgCount,p
     * ostSpreadType.parameterCount(),argArrayType); MethodHandle arrayInvoker = MethodHandles.invoker(preSp
     * readType); MethodHandle makeSpreader = MethodHandles.insertArguments(Lazy.MH_asSpreader,1,argArrayTyp
     * e,spreadArgCount); return MethodHandles.filterArgument(arrayInvoker,0,makeSpreader); }}。
     * asSpreader(argArrayType,spreadArgCount); } //无法在这里构建类型为ginvoker.invoke(mh,a * [254])的通用调用器。
     * 
     * private static Class <?> impliedRestargType(MethodType restargType,int fromPos){if(restargType.isGeneric())return Object []。
     *  //可以没有其他int maxPos = restargType.parameterCount(); if(fromPos> = maxPos)return Object []。
     * class; // rational default Class <?> argType = restargType.parameterType(fromPos); for(int i = fromPo
     * s + 1; i <maxPos; i ++){if(argType！= restargType.parameterType(i))throw newIllegalArgumentException("need homogeneous rest arguments",restargType); } if(argType == Object.class)return Object []。
     *  //可以没有其他int maxPos = restargType.parameterCount(); if(fromPos> = maxPos)return Object []。
     *  return Array.newInstance(argType,0).getClass(); }}。
     * 
     *  public String toString(){return"Invokers"+ targetType; }}
     * 
     *  static MemberName methodHandleInvokeLinkerMethod(String name,MethodType mtype,Object [] appendixResu
     * lt){int which; switch(name){case"invokeExact"：which = MethodTypeForm.LF_EX_LINKER;打破; case"invoke"：which = MethodTypeForm.LF_GEN_LINKER;打破; default：throw new InternalError("not invoker："+ name); }
     *  LambdaForm lform; if(mtype.parameterSlotCount()<= MethodType.MAX_MH_ARITY  -  MH_LINKER_ARG_APPENDED){lform = invokeHandleForm(mtype,false,which); appendixResult [0] = mtype; } else {lform = invokeHandleForm(mtype,true,which); } return lform.vmentry; }}。
     * 
     * 
     * @param mtype the caller's method type (either basic or full-custom)
     * @param customized whether to use a trailing appendix argument (to carry the mtype)
     * @param which bit-encoded 0x01 whether it is a CP adapter ("linker") or MHs.invoker value ("invoker");
     *                          0x02 whether it is for invokeExact or generic invoke
     */
    private static LambdaForm invokeHandleForm(MethodType mtype, boolean customized, int which) {
        boolean isCached;
        if (!customized) {
            mtype = mtype.basicType();  // normalize Z to I, String to Object, etc.
            isCached = true;
        } else {
            isCached = false;  // maybe cache if mtype == mtype.basicType()
        }
        boolean isLinker, isGeneric;
        String debugName;
        switch (which) {
        case MethodTypeForm.LF_EX_LINKER:   isLinker = true;  isGeneric = false; debugName = "invokeExact_MT"; break;
        case MethodTypeForm.LF_EX_INVOKER:  isLinker = false; isGeneric = false; debugName = "exactInvoker"; break;
        case MethodTypeForm.LF_GEN_LINKER:  isLinker = true;  isGeneric = true;  debugName = "invoke_MT"; break;
        case MethodTypeForm.LF_GEN_INVOKER: isLinker = false; isGeneric = true;  debugName = "invoker"; break;
        default: throw new InternalError();
        }
        LambdaForm lform;
        if (isCached) {
            lform = mtype.form().cachedLambdaForm(which);
            if (lform != null)  return lform;
        }
        // exactInvokerForm (Object,Object)Object
        //   link with java.lang.invoke.MethodHandle.invokeBasic(MethodHandle,Object,Object)Object/invokeSpecial
        final int THIS_MH      = 0;
        final int CALL_MH      = THIS_MH + (isLinker ? 0 : 1);
        final int ARG_BASE     = CALL_MH + 1;
        final int OUTARG_LIMIT = ARG_BASE + mtype.parameterCount();
        final int INARG_LIMIT  = OUTARG_LIMIT + (isLinker && !customized ? 1 : 0);
        int nameCursor = OUTARG_LIMIT;
        final int MTYPE_ARG    = customized ? -1 : nameCursor++;  // might be last in-argument
        final int CHECK_TYPE   = nameCursor++;
        final int LINKER_CALL  = nameCursor++;
        MethodType invokerFormType = mtype.invokerType();
        if (isLinker) {
            if (!customized)
                invokerFormType = invokerFormType.appendParameterTypes(MemberName.class);
        } else {
            invokerFormType = invokerFormType.invokerType();
        }
        Name[] names = arguments(nameCursor - INARG_LIMIT, invokerFormType);
        assert(names.length == nameCursor)
                : Arrays.asList(mtype, customized, which, nameCursor, names.length);
        if (MTYPE_ARG >= INARG_LIMIT) {
            assert(names[MTYPE_ARG] == null);
            BoundMethodHandle.SpeciesData speciesData = BoundMethodHandle.speciesData_L();
            names[THIS_MH] = names[THIS_MH].withConstraint(speciesData);
            NamedFunction getter = speciesData.getterFunction(0);
            names[MTYPE_ARG] = new Name(getter, names[THIS_MH]);
            // else if isLinker, then MTYPE is passed in from the caller (e.g., the JVM)
        }

        // Make the final call.  If isGeneric, then prepend the result of type checking.
        MethodType outCallType = mtype.basicType();
        Object[] outArgs = Arrays.copyOfRange(names, CALL_MH, OUTARG_LIMIT, Object[].class);
        Object mtypeArg = (customized ? mtype : names[MTYPE_ARG]);
        if (!isGeneric) {
            names[CHECK_TYPE] = new Name(NF_checkExactType, names[CALL_MH], mtypeArg);
            // mh.invokeExact(a*):R => checkExactType(mh, TYPEOF(a*:R)); mh.invokeBasic(a*)
        } else {
            names[CHECK_TYPE] = new Name(NF_checkGenericType, names[CALL_MH], mtypeArg);
            // mh.invokeGeneric(a*):R => checkGenericType(mh, TYPEOF(a*:R)).invokeBasic(a*)
            outArgs[0] = names[CHECK_TYPE];
        }
        names[LINKER_CALL] = new Name(outCallType, outArgs);
        lform = new LambdaForm(debugName, INARG_LIMIT, names);
        if (isLinker)
            lform.compileToBytecode();  // JVM needs a real methodOop
        if (isCached)
            lform = mtype.form().setCachedLambdaForm(which, lform);
        return lform;
    }

    /*non-public*/ static
    WrongMethodTypeException newWrongMethodTypeException(MethodType actual, MethodType expected) {
        // FIXME: merge with JVM logic for throwing WMTE
        return new WrongMethodTypeException("expected "+expected+" but found "+actual);
    }

    /* <p>
    /*  //参数计数以考虑尾随的"附录值"(通常为mtype)private static final int MH_LINKER_ARG_APPENDED = 1;
    /* 
    /* / **返回invokeExact或泛型调用的适配器,作为MH或常量池链接器。如果！customize,调用者负责在适配器执行期间提供确切mtype的副本。这是因为适配器可能被概括为基本类型。
    /* 
    /* 
    /** Static definition of MethodHandle.invokeExact checking code. */
    /*non-public*/ static
    @ForceInline
    void checkExactType(Object mhObj, Object expectedObj) {
        MethodHandle mh = (MethodHandle) mhObj;
        MethodType expected = (MethodType) expectedObj;
        MethodType actual = mh.type();
        if (actual != expected)
            throw newWrongMethodTypeException(expected, actual);
    }

    /** Static definition of MethodHandle.invokeGeneric checking code.
     * Directly returns the type-adjusted MH to invoke, as follows:
     * {@code (R)MH.invoke(a*) => MH.asType(TYPEOF(a*:R)).invokeBasic(a*)}
     * <p>
     *  WrongMethodTypeException newWrongMethodTypeException(MethodType actual,MethodType expected){// FIXME：merge with JVM logic for throwing WMTE return new WrongMethodTypeException("expected"+ expected +"but found"+ actual); }
     * }。
     * 
     */
    /*non-public*/ static
    @ForceInline
    Object checkGenericType(Object mhObj, Object expectedObj) {
        MethodHandle mh = (MethodHandle) mhObj;
        MethodType expected = (MethodType) expectedObj;
        return mh.asType(expected);
        /* Maybe add more paths here.  Possible optimizations:
         * for (R)MH.invoke(a*),
         * let MT0 = TYPEOF(a*:R), MT1 = MH.type
         *
         * if MT0==MT1 or MT1 can be safely called by MT0
         *  => MH.invokeBasic(a*)
         * if MT1 can be safely called by MT0[R := Object]
         *  => MH.invokeBasic(a*) & checkcast(R)
         * if MT1 can be safely called by MT0[* := Object]
         *  => checkcast(A)* & MH.invokeBasic(a*) & checkcast(R)
         * if a big adapter BA can be pulled out of (MT0,MT1)
         *  => BA.invokeBasic(MT0,MH,a*)
         * if a local adapter LA can cached on static CS0 = new GICS(MT0)
         *  => CS0.LA.invokeBasic(MH,a*)
         * else
         *  => MH.asType(MT0).invokeBasic(A*)
         * <p>
         *  @ForceInline void checkExactType(Object mhObj,Object expectedObj){MethodHandle mh =(MethodHandle)mhObj; MethodType expected =(MethodType)expectedObj; MethodType actual = mh.type(); if(actual！= expected)throw newWrongMethodTypeException(expected,actual); }
         * }。
         * 
         *  / ** MethodHandle.invokeGeneric的静态定义检查代码。
         * 直接返回要调用的类型调整的MH,如下：{@code(R)MH.invoke(a *)=> MH.asType(TYPEOF(a *：R))。invokeBasic(a *)}。
         * 
         */
    }

    static MemberName linkToCallSiteMethod(MethodType mtype) {
        LambdaForm lform = callSiteForm(mtype, false);
        return lform.vmentry;
    }

    static MemberName linkToTargetMethod(MethodType mtype) {
        LambdaForm lform = callSiteForm(mtype, true);
        return lform.vmentry;
    }

    // skipCallSite is true if we are optimizing a ConstantCallSite
    private static LambdaForm callSiteForm(MethodType mtype, boolean skipCallSite) {
        mtype = mtype.basicType();  // normalize Z to I, String to Object, etc.
        final int which = (skipCallSite ? MethodTypeForm.LF_MH_LINKER : MethodTypeForm.LF_CS_LINKER);
        LambdaForm lform = mtype.form().cachedLambdaForm(which);
        if (lform != null)  return lform;
        // exactInvokerForm (Object,Object)Object
        //   link with java.lang.invoke.MethodHandle.invokeBasic(MethodHandle,Object,Object)Object/invokeSpecial
        final int ARG_BASE     = 0;
        final int OUTARG_LIMIT = ARG_BASE + mtype.parameterCount();
        final int INARG_LIMIT  = OUTARG_LIMIT + 1;
        int nameCursor = OUTARG_LIMIT;
        final int APPENDIX_ARG = nameCursor++;  // the last in-argument
        final int CSITE_ARG    = skipCallSite ? -1 : APPENDIX_ARG;
        final int CALL_MH      = skipCallSite ? APPENDIX_ARG : nameCursor++;  // result of getTarget
        final int LINKER_CALL  = nameCursor++;
        MethodType invokerFormType = mtype.appendParameterTypes(skipCallSite ? MethodHandle.class : CallSite.class);
        Name[] names = arguments(nameCursor - INARG_LIMIT, invokerFormType);
        assert(names.length == nameCursor);
        assert(names[APPENDIX_ARG] != null);
        if (!skipCallSite)
            names[CALL_MH] = new Name(NF_getCallSiteTarget, names[CSITE_ARG]);
        // (site.)invokedynamic(a*):R => mh = site.getTarget(); mh.invokeBasic(a*)
        final int PREPEND_MH = 0, PREPEND_COUNT = 1;
        Object[] outArgs = Arrays.copyOfRange(names, ARG_BASE, OUTARG_LIMIT + PREPEND_COUNT, Object[].class);
        // prepend MH argument:
        System.arraycopy(outArgs, 0, outArgs, PREPEND_COUNT, outArgs.length - PREPEND_COUNT);
        outArgs[PREPEND_MH] = names[CALL_MH];
        names[LINKER_CALL] = new Name(mtype, outArgs);
        lform = new LambdaForm((skipCallSite ? "linkToTargetMethod" : "linkToCallSite"), INARG_LIMIT, names);
        lform.compileToBytecode();  // JVM needs a real methodOop
        lform = mtype.form().setCachedLambdaForm(which, lform);
        return lform;
    }

    /** Static definition of MethodHandle.invokeGeneric checking code. */
    /*non-public*/ static
    @ForceInline
    Object getCallSiteTarget(Object site) {
        return ((CallSite)site).getTarget();
    }

    // Local constant functions:
    private static final NamedFunction
        NF_checkExactType,
        NF_checkGenericType,
        NF_getCallSiteTarget;
    static {
        try {
            NamedFunction nfs[] = {
                NF_checkExactType = new NamedFunction(Invokers.class
                        .getDeclaredMethod("checkExactType", Object.class, Object.class)),
                NF_checkGenericType = new NamedFunction(Invokers.class
                        .getDeclaredMethod("checkGenericType", Object.class, Object.class)),
                NF_getCallSiteTarget = new NamedFunction(Invokers.class
                        .getDeclaredMethod("getCallSiteTarget", Object.class))
            };
            for (NamedFunction nf : nfs) {
                // Each nf must be statically invocable or we get tied up in our bootstraps.
                assert(InvokerBytecodeGenerator.isStaticallyInvocable(nf.member)) : nf;
                nf.resolve();
            }
        } catch (ReflectiveOperationException ex) {
            throw newInternalError(ex);
        }
    }

    private static class Lazy {
        private static final MethodHandle MH_asSpreader;

        static {
            try {
                MH_asSpreader = IMPL_LOOKUP.findVirtual(MethodHandle.class, "asSpreader",
                        MethodType.methodType(MethodHandle.class, Class.class, int.class));
            } catch (ReflectiveOperationException ex) {
                throw newInternalError(ex);
            }
        }
    }
}
