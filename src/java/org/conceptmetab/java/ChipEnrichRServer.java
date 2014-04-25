package org.conceptmetab.java;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public final class ChipEnrichRServer
{
	private RConnection connection;

	public ChipEnrichRServer(String serverAddress, int serverPort)
	{
		connectToRserve(serverAddress, serverPort);
		
	}

	private void connectToRserve(String serverAddress, int serverPort)
	{
		try
		{
			
			connection = new RConnection(serverAddress, serverPort);
			connection.voidEval("library('gplots')");
			connection.voidEval("m = read.table('/tmp/no.txt', header = TRUE,sep=\"" +";"+"\" )");
			connection.voidEval("data = data.frame(m[2:nrow(m), 2:ncol(m)], row.names=m[2:nrow(m),1],stringsAsFactors=F)");
			connection.voidEval("hclust.ave <- function(x) hclust(x, method='average')");
			connection.voidEval("par(bg='#F7F8E0')");
			connection.voidEval("myCol <- c('white','#ffffcc','#ffff99','#ffff66','#ffff33','#FFFF00','#FFCC00','#ff9900','#ff6600','#FF3300','red' )");
			connection.voidEval(" myBreaks <- c(0,1, 10, 20,30,40,50,60,70,80,90,100)");
			
			//println("its in pdf block");
				connection.voidEval("pdf('/tmp/test3.pdf')");				
			
			connection.voidEval("test2 =heatmap.2((data.matrix(data)),hclustfun=hclust.ave,col = myCol , breaks = myBreaks,trace='none' )");
			connection.voidEval("dev.off()");
		}
		catch (RserveException e)
		{
			throw new IllegalStateException("Unable to connect to RServer: " + serverAddress + ":" + serverPort);
		}
	}

	

	public void voidEvalRCommand(String command)
	{
		try
		{
			connection.voidEval(command);
		}
		catch (RserveException e)
		{
			e.printStackTrace();
			throw new IllegalStateException("Unable to run command on RServer: " + command);
		}
	}

	public REXP evalRCommand(String command)
	{
		try
		{
			return connection.eval(command);
		}
		catch (RserveException e)
		{
			e.printStackTrace();
			throw new IllegalStateException("Unable to run command on RServer: " + command);
		}
	}
	
	@SuppressWarnings("finally")
	public String parseAndEvalCommand(String command) throws REngineException, REXPMismatchException
	{
		String status = "";
		try
		{
			REXP r1=  connection.parseAndEval("try("+command+",silent=TRUE)");
			if (r1.inherits("try-error")) 
			{
			System.err.println("Error: "+r1.asString());
			status = "Error: "+r1.asString();
			}
		else { System.out.println("Hello");
			status = "Done";
		}
			
		}
		catch (RserveException e)
		{
			e.printStackTrace();
			throw new IllegalStateException("Unable to run command  parseAndEval on RServer: " + command);
		}
		finally
		{
		return status;
		}
	}

	public void assignRVariable(String rvar, double[] values)
	{
		try
		{
			connection.assign(rvar, values);
		}
		catch (REngineException e)
		{
			e.printStackTrace();
			throw new IllegalStateException("Unable to assign to assign variable on RServer: " + rvar);
		}
	}

	public void assignRVariable(String rvar, String[] values)
	{
		try
		{
			System.out.printf(rvar+ " = " +values.toString() + "\n");
			
			connection.assign(rvar, values);
		}
		catch (REngineException e)
		{
			e.printStackTrace();
			throw new IllegalStateException("Unable to assign to assign variable on RServer: " + rvar);
		}
	}

	public void assignRVariable(String rvar, int[] values)
	{
		try
		{
			connection.assign(rvar, values);
		}
		catch (REngineException e)
		{
			e.printStackTrace();
			throw new IllegalStateException("Unable to assign to assign variable on RServer: " + rvar);
		}
	}

	public void assignRVariable(String rvar, String arg)
	{
		try
		{
			System.out.printf(rvar+" = "+arg+ "\n");
			connection.assign(rvar, arg);
		}
		catch (REngineException e)
		{
			e.printStackTrace();
			throw new IllegalStateException("Unable to assign to assign variable on RServer: " + rvar);
		}
	}

	public void close()
	{
		connection.close();
	}
}
