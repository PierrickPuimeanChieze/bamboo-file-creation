package ut.com.cleitech.atlassian;

import org.junit.Test;
import com.cleitech.atlassian.MyPluginComponent;
import com.cleitech.atlassian.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}