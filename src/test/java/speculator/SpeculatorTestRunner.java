package speculator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class SpeculatorTestRunner {
	
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(SpeculatorJunit.class);
		
		for(Failure failure : result.getFailures()){
			System.out.println(failure.toString());
		}
		
		System.out.println(result.wasSuccessful());

	}

}
