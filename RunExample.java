/* Copyright (c) 2015-2018 Smart Technology Solutions Limited. All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of Smart
 * Technology Solutions Limited. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Smart
 * Technology Solutions Limited.
 *
 * SMART TECHNOLOGY SOLUTIONS LIMITED MAKES NO REPRESENTATIONS OR WARRANTIES
 * ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SMART TECHNOLOGY SOLUTIONS LIMITED
 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. */

package sts.test.validator.example;

import sts.test.validator.Validator;

public class RunExample {

  public static void main(String[] arguments) {

    Validator validator = new ValidatorExample();

    byte[] message =
        { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x14 };
    System.out.println("Message is valid: " + validator.isValid(message));

    byte[] valid_message1 =
        { 0x02, 0x11, 0x22, 0x03, 0x30 };
    System.out.println("Message is valid: " + validator.isValid(valid_message1));

    byte[] valid_message2 =
        { 0x02, 0x10, 0x10, 0x20, 0x10, 0x03, 0x03, 0x33 };
    System.out.println("Message is valid: " + validator.isValid(valid_message2));

    // Invalid test: Missing STX
    byte[] invalid_message1 = 
        { 0x10, 0x02, 0x02, 0x01, 0x10, 0x03, 0x16 };
    System.out.println("Message is valid: " + validator.isValid(invalid_message1));

    // Invalid test: Wrong LRC
    byte[] invalid_message2 =
        { 0x02, 0x11, 0x22, 0x03, 0x00 };
    System.out.println("Message is valid: " + validator.isValid(invalid_message2));

    // Invalid test: Missing ETX
    byte[] invalid_message3 = 
        { 0x02, 0x11, 0x22, 0x30 };
    System.out.println("Message is valid: " + validator.isValid(invalid_message3));

  }
}
