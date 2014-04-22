//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.Tools;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import java.util.Calendar;
import java.util.Date;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class TraceLogger   
{
    public static boolean EnableTimeStamp = true;
    public enum LoggingLevel
    {
        None,
        Error,
        Warning,
        Info,
        Debug,
        DebugInternal
    }
    static DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel _logLevel = DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Info;
    public static DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel getLogLevel() throws Exception {
        return TraceLogger._logLevel;
    }

    public static void setLogLevel(DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel value) throws Exception {
        TraceLogger._logLevel = value;
    }

    private static void writeLine(String msg, DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel level) throws Exception {
        if (_logLevel >= level && EnableTimeStamp)
        {
            try
            {
                System.Diagnostics.Trace.WriteLine(String.format(StringSupport.CSFmtStrToJFmtStr("{0} " + msg),Calendar.getInstance().getTime()));
            }
            catch (Exception __dummyCatchVar0)
            {
                System.Diagnostics.Trace.WriteLine("The tracing of the folloging message throw an error: " + msg);
            }
        
        }
        else if (_logLevel >= level)
        {
            System.Diagnostics.Trace.WriteLine(msg);
        }
          
    }

    /**
    * Write a line on error level (is written if level != none)
    * 
    *  @param msg 
    *  @param objs
    */
    public static void simple(String msg, Object... objs) throws Exception {
        if (msg == null || StringSupport.equals(msg, ""))
            return ;
         
        writeLine(String.format(StringSupport.CSFmtStrToJFmtStr(msg),objs),DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Error);
    }

    public static void debugInternal(String msg, Object... objs) throws Exception {
        if (msg == null || StringSupport.equals(msg, ""))
            return ;
         
        writeLine("[D] " + String.format(StringSupport.CSFmtStrToJFmtStr(msg),objs),DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.DebugInternal);
    }

    public static void debug(String msg, Object... objs) throws Exception {
        if (msg == null || StringSupport.equals(msg, ""))
            return ;
         
        writeLine("[D] " + String.format(StringSupport.CSFmtStrToJFmtStr(msg),objs),DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Debug);
    }

    public static void info(String msg, Object... objs) throws Exception {
        if (msg == null || StringSupport.equals(msg, ""))
            return ;
         
        writeLine("[I] " + String.format(StringSupport.CSFmtStrToJFmtStr(msg),objs),DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Info);
    }

    public static void warning(String msg, Object... objs) throws Exception {
        if (msg == null || StringSupport.equals(msg, ""))
            return ;
         
        writeLine("[W] " + String.format(StringSupport.CSFmtStrToJFmtStr(msg),objs),DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Warning);
    }

    public static void error(String msg, Object... objs) throws Exception {
        if (msg == null || StringSupport.equals(msg, ""))
            return ;
         
        writeLine("[E] " + String.format(StringSupport.CSFmtStrToJFmtStr(msg),objs),DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Error);
    }

}


