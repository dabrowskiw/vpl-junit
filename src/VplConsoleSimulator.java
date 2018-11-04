import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simulates console input and output
 * @author gue
 *
 */
public class VplConsoleSimulator
{
	private Process process;
	private BufferedReader inReader;
	private BufferedWriter outWriter;
	private BufferedReader errReader;
	private Boolean immideateLogToConsole = false;
	private List<String> consolewindow = new ArrayList<>();
	
	/**
	 * Starts a JVM with the given class. The input and output are redirected, so we can use them 
	 * with the oher methods of this class.
	 * 
	 * @param classname Class which should be started using java
	 * @param args Optional arguments which are handed over to the started jvm. These are then available within the main agruments of the class under test
	 */
	public VplConsoleSimulator(String classname, String ... args)
	{
		
		String jvm_location;
		if (System.getProperty("os.name").startsWith("Win")) 
		{
		    jvm_location = System.getProperties().getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe";
		} 
		else 
		{
		    jvm_location = System.getProperties().getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		}
		
		String currentClasspath = System.getProperty("java.class.path").toString();
		
		
		try 
		{

			 String[] cmdline = (String[]) Stream.concat(Stream.of(new String[]{jvm_location, 	// start another java vm
																		"-cp",currentClasspath, // set the classpath to the current one
																		classname}),			// Class which should be started
					 									Stream.of(args))						// Optional Parameters
					 						.toArray(b -> new String[b]);
			 
			 this.process = new ProcessBuilder(cmdline).start(); // Go for it !
		}
		catch (IOException e) 
		{
			throw new IllegalArgumentException("The class " + classname + "could not be found. " + e.getMessage());
		} 							
		
		// Get the input and output streams
		this.inReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
		this.outWriter= new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), Charset.defaultCharset()));
		this.errReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), Charset.defaultCharset()));
		
	}
	
	/**
	 * Internal logging of each input the user enters to the class under test
	 * @param s
	 */
	private void log_programInput(String s)
	{
		if(immideateLogToConsole == true) 
		{
			System.out.println("< " + s);
		}
		consolewindow.add("< " + s);
	}
	
	/**
	 * Internal logging of each output of the class under test
	 * @param s
	 */
	private void log_programOutput(String s)
	{
		if(immideateLogToConsole == true) 
		{
			System.out.println("> " + s);
		}
		consolewindow.add("> " + s);
	}
	
	/**
	 * Internal logging of each errorof the class under test
	 * @param s
	 */
	private void log_programmError(String s)
	{
		if(immideateLogToConsole == true) 
		{
			System.out.println("! " + s);
		}
		consolewindow.add("! " + s);
	}

	/**
	 * Prints the whole console dialog
	 * @return
	 */
	public String getFullConsoleIO()
	{
		return consolewindow.stream().collect(Collectors.joining("\r\n")).toString();
	}
	

	/**
	 * Returns the full output of the programm since the last call
	 * @return
	 * @throws IOException 
	 */
	public List<String> getOutput() throws IOException
	{
		String line = null;
		List<String> ret = new ArrayList<>();
		while ((line = inReader.readLine()) != null) 
		{
			log_programOutput(line);
			ret.add(line);
		}
		return ret;
	}
	
	/**
	 * Returns the full errorstream of the programm since the last call
	 * @return
	 * @throws IOException 
	 */
	public List<String> getError() throws IOException
	{
		String line = null;
		List<String> ret = new ArrayList<>();
		while ((line = errReader.readLine()) != null) 
		{
			log_programOutput(line);
			ret.add(line);
		}
		return ret;
	}

	/**
	 * Reads the given stream till the next line is not empty
	 * @param br
	 * @return
	 * @throws IOException
	 */
	private String getNextNonEmptyLine(BufferedReader br) throws IOException
	{
		String line = null;
		while ((line = br.readLine()) != null) 
		{
			if(br == this.inReader)
			{
				log_programOutput(line);
			}
			else
			{
				log_programmError(line);
			}
			
			if(line.length() > 0)
			{
				return(line);
			}
		}
		
		return null;
	}
	
	/**
	 * Convinience function: Gives the next Line of the programs stdout which is not an empty line
	 * @return
	 * @throws IOException 
	 */
	public String getNextNonEmptyOutputLine() throws IOException
	{
		return getNextNonEmptyLine(this.inReader);
	}
	
	
	/** 
	 * Skips input till the String is found. This string is consumed - so anything after this string can
	 * be consumed with getOutput. If the String is not found then it reads up all remaining lines.
	 * 
	 * @param string
	 * @throws IOException 
	 */
	public void skipOutputTill(Predicate<String> condition) throws IOException
	{
		String line = null;
		while ((line = inReader.readLine()) != null) 
		{
			log_programOutput(line);
			if(condition.test(line) == true)
			{
				return;
			}
		}	
	}
	
	/**
	 * Fetches the full output and expects the String to be at the end of the output.
	 * @param string
	 * @throws IOException 
	 */
	public boolean expectOutput(Predicate<String> condition) throws IOException
	{
		String line = this.getNextNonEmptyOutputLine();
		if(line == null)
		{
			fail("Line is null");
		}
		boolean result = condition.test(line);
		return result;
	}

	/** 
	 * Convinience function: Expects the last line to be the exact the following value 
	 * @param expectedValue
	 * @return
	 * @throws IOException
	 */
	public boolean expectOutput(String expectedValue) throws IOException
	{
		return this.expectOutput(line -> line.equals(expectedValue));
	}
	
	/**
	 * Fetches the full output and expects the String to be at the end of the output.
	 * @param string
	 * @throws IOException 
	 */
	public void expectError(Predicate<String> condition) throws IOException
	{
		String line = this.getNextNonEmptyLine(this.errReader);
		if(line == null)
		{
			fail("Line is null");
		}
		
		if(condition.test(line) == false)
		{
			fail("Condition not met: " + condition);
		}
	}
	
	/**
	 * Enters a single line into the virtual console. An system dependend line separator 
	 * is added automatically.
	 * 
	 * @param string
	 * @throws IOException 
	 */
	public void enterLine(String string) throws IOException
	{
		this.enter(string + System.lineSeparator());
	}
	
	/**
	 * Enters the given text into the virtual console. Use with caution.
	 * Attention: If you are using this within a dialog, then this function may block your execution.
	 * @param string
	 * @throws IOException 
	 */
	public void enter(String string) throws IOException
	{
		log_programInput(string.trim());
		outWriter.write(string);
		outWriter.flush();
	}

	/**
	 * Waits for the process to exit and returns the exit value
	 * @param i
	 * @throws InterruptedException 
	 */
	public Integer getExitValue() throws InterruptedException 
	{
		process.waitFor();
		return process.exitValue();
	}
	
	/**
	 * Kills the application forcibly
	 */
	public void kill()
	{
		process.destroyForcibly();
	}
}
 