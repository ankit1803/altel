/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.protocol.irc;

import java.util.*;

import junit.framework.*;

/**
 * @author Danny van Heumen
 */
public class ISupportTest
        extends TestCase
{
    public void testParseNullDestination()
    {
        try
        {
            ISupport.parseChanLimit(null, "");
            Assert.fail("Should not reach this, since it should fail on null destination.");
        }
        catch (IllegalArgumentException e)
        {
        }
    }

    public void testParseNullValue()
    {
        HashMap<Character, Integer> dest = new HashMap<Character, Integer>();
        ISupport.parseChanLimit(dest, null);
        Assert.assertEquals(0, dest.size());
    }

    public void testParseEmptyValue()
    {
        Map<Character, Integer> destination = new HashMap<Character, Integer>();
        ISupport.parseChanLimit(destination, "");
        Assert.assertEquals(0, destination.size());
    }

    public void testParseSingleSimpleValidValue()
    {
        Map<Character, Integer> destination = new HashMap<Character, Integer>();
        ISupport.parseChanLimit(destination, "#:10");
        Assert.assertEquals(1, destination.size());
        Assert.assertEquals(10, destination.get('#').intValue());
    }

    public void testParseSingleCombinedValidValue()
    {
        Map<Character, Integer> destination = new HashMap<Character, Integer>();
        ISupport.parseChanLimit(destination, "#&+:25");
        Assert.assertEquals(3, destination.size());
        Assert.assertEquals(25, destination.get('#').intValue());
        Assert.assertEquals(25, destination.get('&').intValue());
        Assert.assertEquals(25, destination.get('+').intValue());
    }

    public void testParseMultipleSimpleValidValues()
    {
        Map<Character, Integer> destination = new HashMap<Character, Integer>();
        ISupport.parseChanLimit(destination, "#:10,&:20,+:30");
        Assert.assertEquals(3, destination.size());
        Assert.assertEquals(10, destination.get('#').intValue());
        Assert.assertEquals(20, destination.get('&').intValue());
        Assert.assertEquals(30, destination.get('+').intValue());
    }

    public void testParseMultipleSimpleValidValuesWithInvalidStuff()
    {
        Map<Character, Integer> destination = new HashMap<Character, Integer>();
        ISupport.parseChanLimit(destination, "#:10,^:20,jadie:abc,+:30,#:abc");
        Assert.assertEquals(2, destination.size());
        Assert.assertEquals(10, destination.get('#').intValue());
        Assert.assertEquals(30, destination.get('+').intValue());
    }

    public void testParseMultipleCombinedValidValues()
    {
        Map<Character, Integer> destination = new HashMap<Character, Integer>();
        ISupport.parseChanLimit(destination, "#&:100,+:30");
        Assert.assertEquals(3, destination.size());
        Assert.assertEquals(100, destination.get('#').intValue());
        Assert.assertEquals(100, destination.get('&').intValue());
        Assert.assertEquals(30, destination.get('+').intValue());
    }

    public void testParseSimpleInvalidValue()
    {
        Map<Character, Integer> destination = new HashMap<Character, Integer>();
        ISupport.parseChanLimit(destination, "bla");
        Assert.assertEquals(0, destination.size());
    }
}
