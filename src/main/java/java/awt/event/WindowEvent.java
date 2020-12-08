/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.event;

import java.awt.Window;
import java.lang.annotation.Native;
import sun.awt.AppContext;
import sun.awt.SunToolkit;

/**
 * A low-level event that indicates that a window has changed its status. This
 * low-level event is generated by a Window object when it is opened, closed,
 * activated, deactivated, iconified, or deiconified, or when focus is
 * transfered into or out of the Window.
 * <P>
 * The event is passed to every <code>WindowListener</code>
 * or <code>WindowAdapter</code> object which registered to receive such
 * events using the window's <code>addWindowListener</code> method.
 * (<code>WindowAdapter</code> objects implement the
 * <code>WindowListener</code> interface.) Each such listener object
 * gets this <code>WindowEvent</code> when the event occurs.
 * <p>
 * An unspecified behavior will be caused if the {@code id} parameter
 * of any particular {@code WindowEvent} instance is not
 * in the range from {@code WINDOW_FIRST} to {@code WINDOW_LAST}.
 *
 * <p>
 *  指示窗口已更改其状态的低级事件。此低级事件由窗口对象在打开,关闭,激活,取消激活,图标化或取消孤立时,或者当焦点被传入或移出窗口时生成。
 * <P>
 *  事件被传递到每个使用窗口的<code> addWindowListener </code>方法注册接收这些事件的每个<code> WindowListener </code>或<code> Windo
 * wAdapter </code>对象。
 *  (<code> WindowAdapter </code>对象实现<code> WindowListener </code>接口。
 * )当事件发生时,每个这样的监听器对象获得这<code> WindowEvent </code>。
 * <p>
 *  如果任何特定{@code WindowEvent}实例的{@code id}参数不在{@code WINDOW_FIRST}到{@code WINDOW_LAST}的范围内,则会导致未指定的行为。
 * 
 * 
 * @author Carl Quinn
 * @author Amy Fowler
 *
 * @see WindowAdapter
 * @see WindowListener
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html">Tutorial: Writing a Window Listener</a>
 *
 * @since JDK1.1
 */
public class WindowEvent extends ComponentEvent {

    /**
     * The first number in the range of ids used for window events.
     * <p>
     *  用于窗口事件的ids范围中的第一个数字。
     * 
     */
    public static final int WINDOW_FIRST        = 200;

    /**
     * The window opened event.  This event is delivered only
     * the first time a window is made visible.
     * <p>
     *  窗口打开事件。此事件仅在第一次使窗口可见时传递。
     * 
     */
    @Native public static final int WINDOW_OPENED       = WINDOW_FIRST; // 200

    /**
     * The "window is closing" event. This event is delivered when
     * the user attempts to close the window from the window's system menu.
     * If the program does not explicitly hide or dispose the window
     * while processing this event, the window close operation will be
     * cancelled.
     * <p>
     *  "窗口正在关闭"事件。当用户尝试从窗口的系统菜单关闭窗口时,将发送此事件。如果程序在处理此事件时没有显式隐藏或处理窗口,则窗口关闭操作将被取消。
     * 
     */
    @Native public static final int WINDOW_CLOSING      = 1 + WINDOW_FIRST; //Event.WINDOW_DESTROY

    /**
     * The window closed event. This event is delivered after the displayable
     * window has been closed as the result of a call to dispose.
     * <p>
     * 窗口关闭事件。由于调用调用的结果,在可显示窗口关闭后,将发送此事件。
     * 
     * 
     * @see java.awt.Component#isDisplayable
     * @see Window#dispose
     */
    @Native public static final int WINDOW_CLOSED       = 2 + WINDOW_FIRST;

    /**
     * The window iconified event. This event is delivered when
     * the window has been changed from a normal to a minimized state.
     * For many platforms, a minimized window is displayed as
     * the icon specified in the window's iconImage property.
     * <p>
     *  窗口图标化事件。当窗口从正常状态更改为最小化状态时,将传递此事件。对于许多平台,最小化的窗口显示为在窗口的iconImage属性中指定的图标。
     * 
     * 
     * @see java.awt.Frame#setIconImage
     */
    @Native public static final int WINDOW_ICONIFIED    = 3 + WINDOW_FIRST; //Event.WINDOW_ICONIFY

    /**
     * The window deiconified event type. This event is delivered when
     * the window has been changed from a minimized to a normal state.
     * <p>
     *  窗口deiconified事件类型。当窗口从最小化状态更改为正常状态时,将传递此事件。
     * 
     */
    @Native public static final int WINDOW_DEICONIFIED  = 4 + WINDOW_FIRST; //Event.WINDOW_DEICONIFY

    /**
     * The window-activated event type. This event is delivered when the Window
     * becomes the active Window. Only a Frame or a Dialog can be the active
     * Window. The native windowing system may denote the active Window or its
     * children with special decorations, such as a highlighted title bar. The
     * active Window is always either the focused Window, or the first Frame or
     * Dialog that is an owner of the focused Window.
     * <p>
     *  窗口激活的事件类型。当窗口变为活动窗口时,将传递此事件。只有框架或对话框可以是活动窗口。本地加窗系统可以表示活动窗口或其具有特殊装饰的子项,例如突出显示的标题栏。
     * 活动窗口始终是关注的窗口,或者是焦点窗口的所有者的第一个框架或对话框。
     * 
     */
    @Native public static final int WINDOW_ACTIVATED    = 5 + WINDOW_FIRST;

    /**
     * The window-deactivated event type. This event is delivered when the
     * Window is no longer the active Window. Only a Frame or a Dialog can be
     * the active Window. The native windowing system may denote the active
     * Window or its children with special decorations, such as a highlighted
     * title bar. The active Window is always either the focused Window, or the
     * first Frame or Dialog that is an owner of the focused Window.
     * <p>
     *  窗口停用的事件类型。当窗口不再是活动窗口时,将传递此事件。只有框架或对话框可以是活动窗口。本地加窗系统可以表示活动窗口或其具有特殊装饰的子项,例如突出显示的标题栏。
     * 活动窗口始终是关注的窗口,或者是焦点窗口的所有者的第一个框架或对话框。
     * 
     */
    @Native public static final int WINDOW_DEACTIVATED  = 6 + WINDOW_FIRST;

    /**
     * The window-gained-focus event type. This event is delivered when the
     * Window becomes the focused Window, which means that the Window, or one
     * of its subcomponents, will receive keyboard events.
     * <p>
     * 窗口获取焦点事件类型。当窗口变为关注窗口时,将传递此事件,这意味着窗口或其子组件之一将接收键盘事件。
     * 
     */
    @Native public static final int WINDOW_GAINED_FOCUS = 7 + WINDOW_FIRST;

    /**
     * The window-lost-focus event type. This event is delivered when a Window
     * is no longer the focused Window, which means keyboard events will no
     * longer be delivered to the Window or any of its subcomponents.
     * <p>
     *  窗口失去焦点事件类型。当窗口不再是关注的窗口时,将传递此事件,这意味着键盘事件将不再传递到窗口或其任何子组件。
     * 
     */
    @Native public static final int WINDOW_LOST_FOCUS   = 8 + WINDOW_FIRST;

    /**
     * The window-state-changed event type.  This event is delivered
     * when a Window's state is changed by virtue of it being
     * iconified, maximized etc.
     * <p>
     *  窗口状态更改的事件类型。当窗口的状态由于被图标化,最大化等而改变时,该事件被传递。
     * 
     * 
     * @since 1.4
     */
    @Native public static final int WINDOW_STATE_CHANGED = 9 + WINDOW_FIRST;

    /**
     * The last number in the range of ids used for window events.
     * <p>
     *  用于窗口事件的ids范围中的最后一个数字。
     * 
     */
    public static final int WINDOW_LAST         = WINDOW_STATE_CHANGED;

    /**
     * The other Window involved in this focus or activation change. For a
     * WINDOW_ACTIVATED or WINDOW_GAINED_FOCUS event, this is the Window that
     * lost activation or focus. For a WINDOW_DEACTIVATED or WINDOW_LOST_FOCUS
     * event, this is the Window that gained activation or focus. For any other
     * type of WindowEvent, or if the focus or activation change occurs with a
     * native application, a Java application in a different VM, or with no
     * other Window, null is returned.
     *
     * <p>
     *  涉及此焦点或激活的其他窗口更改。对于WINDOW_ACTIVATED或WINDOW_GAINED_FOCUS事件,这是失去激活或焦点的窗口。
     * 对于WINDOW_DEACTIVATED或WINDOW_LOST_FOCUS事件,这是获得激活或焦点的窗口。
     * 对于任何其他类型的WindowEvent,或者如果本地应用程序发生焦点或激活更改,则返回不同VM中的Java应用程序,或不返回其他Window的null。
     * 
     * 
     * @see #getOppositeWindow
     * @since 1.4
     */
    transient Window opposite;

    /**
     * TBS
     * <p>
     *  TBS
     * 
     */
    int oldState;
    int newState;


    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = -1567959133147912127L;


    /**
     * Constructs a <code>WindowEvent</code> object.
     * <p>This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * <p>
     *  构造一个<code> WindowEvent </code>对象。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source    The <code>Window</code> object
     *                    that originated the event
     * @param id        An integer indicating the type of event.
     *                     For information on allowable values, see
     *                     the class description for {@link WindowEvent}
     * @param opposite  The other window involved in the focus or activation
     *                      change, or <code>null</code>
     * @param oldState  Previous state of the window for window state change event.
     *                  See {@code #getOldState()} for allowable values
     * @param newState  New state of the window for window state change event.
     *                  See {@code #getNewState()} for allowable values
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see #getWindow()
     * @see #getID()
     * @see #getOppositeWindow()
     * @see #getOldState()
     * @see #getNewState()
     * @since 1.4
     */
    public WindowEvent(Window source, int id, Window opposite,
                       int oldState, int newState)
    {
        super(source, id);
        this.opposite = opposite;
        this.oldState = oldState;
        this.newState = newState;
    }

    /**
     * Constructs a <code>WindowEvent</code> object with the
     * specified opposite <code>Window</code>. The opposite
     * <code>Window</code> is the other <code>Window</code>
     * involved in this focus or activation change.
     * For a <code>WINDOW_ACTIVATED</code> or
     * <code>WINDOW_GAINED_FOCUS</code> event, this is the
     * <code>Window</code> that lost activation or focus.
     * For a <code>WINDOW_DEACTIVATED</code> or
     * <code>WINDOW_LOST_FOCUS</code> event, this is the
     * <code>Window</code> that gained activation or focus.
     * If this focus change occurs with a native application, with a
     * Java application in a different VM, or with no other
     * <code>Window</code>, then the opposite Window is <code>null</code>.
     * <p>This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * <p>
     * 构造具有指定的相反<code> Window </code>的<code> WindowEvent </code>对象。
     * 相反的<code> Window </code>是这个焦点或激活改变中涉及的另一个<code> Window </code>。
     * 对于<code> WINDOW_ACTIVATED </code>或<code> WINDOW_GAINED_FOCUS </code>事件,这是失去激活或焦点的<code> Window </code>
     * 。
     * 相反的<code> Window </code>是这个焦点或激活改变中涉及的另一个<code> Window </code>。
     * 对于<code> WINDOW_DEACTIVATED </code>或<code> WINDOW_LOST_FOCUS </code>事件,这是获得激活或焦点的<code> Window </code>
     * 。
     * 相反的<code> Window </code>是这个焦点或激活改变中涉及的另一个<code> Window </code>。
     * 如果本地应用程序,Java应用程序在不同VM中或没有其他<code> Window </code>时发生此焦点更改,则相反的窗口为<code> null </code>。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source     The <code>Window</code> object that
     *                   originated the event
     * @param id        An integer indicating the type of event.
     *                     For information on allowable values, see
     *                     the class description for {@link WindowEvent}.
     *                  It is expected that this constructor will not
     *                  be used for other then
     *                  {@code WINDOW_ACTIVATED},{@code WINDOW_DEACTIVATED},
     *                  {@code WINDOW_GAINED_FOCUS}, or {@code WINDOW_LOST_FOCUS}.
     *                  {@code WindowEvent} types,
     *                  because the opposite <code>Window</code> of other event types
     *                  will always be {@code null}.
     * @param opposite   The other <code>Window</code> involved in the
     *                   focus or activation change, or <code>null</code>
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see #getWindow()
     * @see #getID()
     * @see #getOppositeWindow()
     * @since 1.4
     */
    public WindowEvent(Window source, int id, Window opposite) {
        this(source, id, opposite, 0, 0);
    }

    /**
     * Constructs a <code>WindowEvent</code> object with the specified
     * previous and new window states.
     * <p>This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * <p>
     *  构造具有指定的上一个和新的窗口状态的<code> WindowEvent </code>对象。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source    The <code>Window</code> object
     *                  that originated the event
     * @param id        An integer indicating the type of event.
     *                     For information on allowable values, see
     *                     the class description for {@link WindowEvent}.
     *                  It is expected that this constructor will not
     *                  be used for other then
     *                  {@code WINDOW_STATE_CHANGED}
     *                  {@code WindowEvent}
     *                  types, because the previous and new window
     *                  states are meaningless for other event types.
     * @param oldState  An integer representing the previous window state.
     *                  See {@code #getOldState()} for allowable values
     * @param newState  An integer representing the new window state.
     *                  See {@code #getNewState()} for allowable values
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see #getWindow()
     * @see #getID()
     * @see #getOldState()
     * @see #getNewState()
     * @since 1.4
     */
    public WindowEvent(Window source, int id, int oldState, int newState) {
        this(source, id, null, oldState, newState);
    }

    /**
     * Constructs a <code>WindowEvent</code> object.
     * <p>This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * <p>
     *  构造一个<code> WindowEvent </code>对象。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source The <code>Window</code> object that originated the event
     * @param id     An integer indicating the type of event.
     *                     For information on allowable values, see
     *                     the class description for {@link WindowEvent}.
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see #getWindow()
     * @see #getID()
     */
    public WindowEvent(Window source, int id) {
        this(source, id, null, 0, 0);
    }

    /**
     * Returns the originator of the event.
     *
     * <p>
     *  返回事件的发起者。
     * 
     * 
     * @return the Window object that originated the event
     */
    public Window getWindow() {
        return (source instanceof Window) ? (Window)source : null;
    }

    /**
     * Returns the other Window involved in this focus or activation change.
     * For a WINDOW_ACTIVATED or WINDOW_GAINED_FOCUS event, this is the Window
     * that lost activation or focus. For a WINDOW_DEACTIVATED or
     * WINDOW_LOST_FOCUS event, this is the Window that gained activation or
     * focus. For any other type of WindowEvent, or if the focus or activation
     * change occurs with a native application, with a Java application in a
     * different VM or context, or with no other Window, null is returned.
     *
     * <p>
     * 返回此焦点或激活更改中涉及的其他窗口。对于WINDOW_ACTIVATED或WINDOW_GAINED_FOCUS事件,这是失去激活或焦点的窗口。
     * 对于WINDOW_DEACTIVATED或WINDOW_LOST_FOCUS事件,这是获得激活或焦点的窗口。
     * 对于任何其他类型的WindowEvent,或者如果焦点或激活更改发生在本机应用程序中,Java应用程序位于不同的VM或上下文中,或者没有其他Window,则返回null。
     * 
     * 
     * @return the other Window involved in the focus or activation change, or
     *         null
     * @since 1.4
     */
    public Window getOppositeWindow() {
        if (opposite == null) {
            return null;
        }

        return (SunToolkit.targetToAppContext(opposite) ==
                AppContext.getAppContext())
            ? opposite
            : null;
    }

    /**
     * For <code>WINDOW_STATE_CHANGED</code> events returns the
     * previous state of the window. The state is
     * represented as a bitwise mask.
     * <ul>
     * <li><code>NORMAL</code>
     * <br>Indicates that no state bits are set.
     * <li><code>ICONIFIED</code>
     * <li><code>MAXIMIZED_HORIZ</code>
     * <li><code>MAXIMIZED_VERT</code>
     * <li><code>MAXIMIZED_BOTH</code>
     * <br>Concatenates <code>MAXIMIZED_HORIZ</code>
     * and <code>MAXIMIZED_VERT</code>.
     * </ul>
     *
     * <p>
     *  对于<code> WINDOW_STATE_CHANGED </code>事件,返回窗口的上一状态。状态表示为按位掩码。
     * <ul>
     *  <li> <code> NORMAL </code> <br>表示未设置状态位。
     *  <li> <code> ICONIFIED </code> <li> <code> MAXIMIZED_HORIZ </code> <li> <code> MAXIMIZED_VERT </code>
     *  <li> <code> MAXIMIZED_BOTH </code> > MAXIMIZED_HORIZ </code>和<code> MAXIMIZED_VERT </code>。
     *  <li> <code> NORMAL </code> <br>表示未设置状态位。
     * </ul>
     * 
     * 
     * @return a bitwise mask of the previous window state
     * @see java.awt.Frame#getExtendedState()
     * @since 1.4
     */
    public int getOldState() {
        return oldState;
    }

    /**
     * For <code>WINDOW_STATE_CHANGED</code> events returns the
     * new state of the window. The state is
     * represented as a bitwise mask.
     * <ul>
     * <li><code>NORMAL</code>
     * <br>Indicates that no state bits are set.
     * <li><code>ICONIFIED</code>
     * <li><code>MAXIMIZED_HORIZ</code>
     * <li><code>MAXIMIZED_VERT</code>
     * <li><code>MAXIMIZED_BOTH</code>
     * <br>Concatenates <code>MAXIMIZED_HORIZ</code>
     * and <code>MAXIMIZED_VERT</code>.
     * </ul>
     *
     * <p>
     *  对于<code> WINDOW_STATE_CHANGED </code>事件,返回窗口的新状态。状态表示为按位掩码。
     * <ul>
     *  <li> <code> NORMAL </code> <br>表示未设置状态位。
     *  <li> <code> ICONIFIED </code> <li> <code> MAXIMIZED_HORIZ </code> <li> <code> MAXIMIZED_VERT </code>
     *  <li> <code> MAXIMIZED_BOTH </code> > MAXIMIZED_HORIZ </code>和<code> MAXIMIZED_VERT </code>。
     *  <li> <code> NORMAL </code> <br>表示未设置状态位。
     * 
     * @return a bitwise mask of the new window state
     * @see java.awt.Frame#getExtendedState()
     * @since 1.4
     */
    public int getNewState() {
        return newState;
    }

    /**
     * Returns a parameter string identifying this event.
     * This method is useful for event-logging and for debugging.
     *
     * <p>
     * </ul>
     * 
     * 
     * @return a string identifying the event and its attributes
     */
    public String paramString() {
        String typeStr;
        switch(id) {
          case WINDOW_OPENED:
              typeStr = "WINDOW_OPENED";
              break;
          case WINDOW_CLOSING:
              typeStr = "WINDOW_CLOSING";
              break;
          case WINDOW_CLOSED:
              typeStr = "WINDOW_CLOSED";
              break;
          case WINDOW_ICONIFIED:
              typeStr = "WINDOW_ICONIFIED";
              break;
          case WINDOW_DEICONIFIED:
              typeStr = "WINDOW_DEICONIFIED";
              break;
          case WINDOW_ACTIVATED:
              typeStr = "WINDOW_ACTIVATED";
              break;
          case WINDOW_DEACTIVATED:
              typeStr = "WINDOW_DEACTIVATED";
              break;
          case WINDOW_GAINED_FOCUS:
              typeStr = "WINDOW_GAINED_FOCUS";
              break;
          case WINDOW_LOST_FOCUS:
              typeStr = "WINDOW_LOST_FOCUS";
              break;
          case WINDOW_STATE_CHANGED:
              typeStr = "WINDOW_STATE_CHANGED";
              break;
          default:
              typeStr = "unknown type";
        }
        typeStr += ",opposite=" + getOppositeWindow()
            + ",oldState=" + oldState + ",newState=" + newState;

        return typeStr;
    }
}
