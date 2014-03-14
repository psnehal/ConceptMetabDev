

import com.eco.bio7.rbridge.*;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.REngine.REXPDouble;

RConnection c = new RConnection();

c.eval("A<- 5");
c.eval("B<-6");
c.eval("C<-A+B");
def e = c.eval("C<-A+B").asInteger()
println(e)



