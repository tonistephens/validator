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

/**
 * Example implementation of {@link Validator}. Candidates may modify this class
 * or write their own implementation.
 */
public class ValidatorExample implements Validator {

  /* Note: the required no-argument constructor is implicitly defined if no
   * other constructors are provided */

  private static final byte stx = 0x02;
  private static final byte etx = 0x03;
  private static final byte dle = 0x10;

  /**
   * Indicate if the given message is valid.
   *
   * @param message The message to check
   * @return {@code true} if the message starts with STX, ends with ETX and the
   *         correct LRC, and has correctly-escaped data; {@code false}
   *         otherwise.
   * @todo Implement this method
   */

  public boolean isValid(byte[] message) {
    // Null check
    if (message == null) {
      return false;
    }
    // Minimum length check: STX + at least 1 data byte + ETX + LRC = 4 bytes
    if (message.length < 4) {
      return false;
    }
    // Check STX at start
    if (message[0] != stx) {
      return false;
    }
    // Check ETX second-to-last byte
    if (message[message.length-2] != etx) {
      return false;
    }

    // Initialise calculated LRC to 0
    byte lrc = 0;
    // Retrieve LRC from last byte of message
    byte expected_lrc = message[message.length-1];
    // Checks whether processing DLE byte
    boolean esc = false;

    for (int i=1; i < message.length-2; i++) {
      byte current = message[i];
      if (esc) {
        // If previous byte was DLE, byte is escaped and XOR'd
        lrc ^= current;
        esc = false;
      }
      else if (current == dle) {
        // Found DLE; enter escape mode
        esc = true;
        // If DLE is last byte before ETX, it is invalid
        if (i == message.length-3) {
          return false;
        }
      }
      else {
        // Normal data byte: not escaped, not DLE
        lrc ^= current;
      }
    }
    // XOR in ETX byte
    lrc ^= etx;
    // Compare calculated LRC to expected LRC
    return lrc == expected_lrc;
  }
}
