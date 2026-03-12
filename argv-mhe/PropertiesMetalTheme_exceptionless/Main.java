// SPDX-FileCopyrightText: Copyright (c) 1998, 2011, Oracle and/or its affiliates
// SPDX-License-Identifier: GPL-2.0-only
// SPDX-FileCopyrightText: Copyright (C) 2026 The ARG-V Project

/** filtered and transformed by ARG-V */

/*
 * Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */
import org.sosy_lab.sv_benchmarks.Verifier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;


/**
 * This class allows you to load a theme from a file.
 * It uses the standard Java Properties file format.
 * To create a theme you provide a text file which contains
 * tags corresponding to colors of the theme along with a value
 * for that color.  For example:
 *
 * name=My Ugly Theme
 * primary1=255,0,0
 * primary2=0,255,0
 * primary3=0,0,255
 *
 * This class only loads colors from the properties file,
 * but it could easily be extended to load fonts -  or even icons.
 *
 * @author Steve Wilson
 * @author Alexander Kouznetsov
 */
/** filtered by ARG-V */
 public class Main {

    private String name = "Custom Theme";
    private ColorUIResource primary1 = null;
    private ColorUIResource primary2 = null;
    private ColorUIResource primary3 = null;
    private ColorUIResource secondary1 = null;
    private ColorUIResource secondary2 = null;
    private ColorUIResource secondary3 = null;
    private ColorUIResource black = null;
    private ColorUIResource white = null;

    /**
     * pass an inputstream pointing to a properties file.
     * Colors will be initialized to be the same as the DefaultMetalTheme,
     * and then any colors provided in the properties file will override that.
     */
    /** ARG-V: suitable */
	 public Main(InputStream stream) {
        loadProperties(stream);
    }


    /**
     * Load the theme name and colors from the properties file
     * Items not defined in the properties file are ignored
     */
    /** ARG-V: suitable */
	 private void loadProperties(InputStream stream) {
        Properties prop = new Properties();
        try {
            prop.load(stream);
        } catch (Exception e) {
            System.out.println(e);
        }

        Object tempName = prop.get("name");
        if (tempName != null) {
            name = tempName.toString();
        }

        Object colorString = null;

        colorString = prop.get("primary1");
        if (colorString != null) {
            primary1 = parseColor(colorString.toString());
        }

        colorString = prop.get("primary2");
        if (colorString != null) {
            primary2 = parseColor(colorString.toString());
        }

        colorString = prop.get("primary3");
        if (colorString != null) {
            primary3 = parseColor(colorString.toString());
        }

        colorString = prop.get("secondary1");
        if (colorString != null) {
            secondary1 = parseColor(colorString.toString());
        }

        colorString = prop.get("secondary2");
        if (colorString != null) {
            secondary2 = parseColor(colorString.toString());
        }

        colorString = prop.get("secondary3");
        if (colorString != null) {
            secondary3 = parseColor(colorString.toString());
        }

        colorString = prop.get("black");
        if (colorString != null) {
            black = parseColor(colorString.toString());
        }

        colorString = prop.get("white");
        if (colorString != null) {
            white = parseColor(colorString.toString());
        }

    }

    /**
     * parse a comma delimited list of 3 strings into a Color
     */
    /** ARG-V: suitable */
	 private ColorUIResource parseColor(String s) {
        int red = 0;
        int green = 0;
        int blue = 0;
        try {
            StringTokenizer st = new StringTokenizer(s, ",");

            red = Integer.parseInt(st.nextToken());
            green = Integer.parseInt(st.nextToken());
            blue = Integer.parseInt(st.nextToken());

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Couldn't parse color :" + s);
        }

		return new ColorUIResource(red, green, blue);
    }

	/** This main was generated by ARG-V */
	
	public static void main(String[] args) throws Exception {
        InputStream is = new ByteArrayInputStream(Verifier.nondetString().getBytes());
		Main instance = new Main(is);
	}
}
