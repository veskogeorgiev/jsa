/**
 * 
 */
package jsa.thrift.test;

import jsa.compiler.SourceCodeGenerator;
import jsa.compiler.SourceFile;
import jsa.compiler.SourceGenerationContext;
import jsa.compiler.thrift.ThriftGeneratorFactory;
import jsa.thrift.test.mock.api.ItemsAPI;

import org.junit.Test;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class ThrftTest {

    @Test
    public void testSource() {
      ThriftGeneratorFactory factory = new ThriftGeneratorFactory();
      SourceCodeGenerator gen = factory.create(ItemsAPI.class, new SourceGenerationContext("ctx", "ns"));

      for (SourceFile sf : gen.write()) {
          System.out.println(new String(sf.getContent()));
      }
    }
}
