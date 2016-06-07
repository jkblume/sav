package de.jkblume.sav.testsuite;

public class JSensorTest {

//	@Test
//	public testGetSamplingRate() {
//		JSensor sensor = new JSensor("sensor");
//		IProcess process = new TestProcess();
//		IRetrieveStrategy retrieve = new TestRetrieveStrategy();
//		
//		sensor.setIProcess(process);
//		sensor.setIRetrieveStrategy(retrieve);		
//	}
//	
//	private AbstractProcess parseDescription(String fileName) throws FileNotFoundException, IOException {
//        InputStream is = new FileInputStream(fileName);
//        SMLUtils utils = new SMLUtils(SMLUtils.V2_0);
//        AbstractProcess process = utils.readProcess(is);
//        return process;
//	}
//	
//	private class TestRetrieveStrategy implements IRetrieveStrategy {
//
//		private AbstractProcess process;
//		
//		
//		@Override
//		public Object retrieveValue() {
//			return "hello";
//		}
//
//		@Override
//		public AbstractProcess getSmlConfiguration() {
//			return process;
//		}
//
//		@Override
//		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
//			this.process = smlConfiguration;
//		}
//
//
//		@Override
//		public <T> T as(Class<T> c) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//	}
//	
//	private class TestProcess implements IProcess {
//		
//		private AbstractProcess process;
//		
//		@Override
//		public Object execute(Object input) {
//			String castedInput = (String) input;
//			
//			int output = 0;
//			if (castedInput.equals("hello")) {
//				output = 1;
//			}
//			if (castedInput.equals("world")) {
//				output = 2;
//			}
//			
//			return output;
//		}
//
//		@Override
//		public AbstractProcess getSmlConfiguration() {
//			return process;
//		}
//
//		@Override
//		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
//			this.process = smlConfiguration;
//		}
//
//		@Override
//		public <T> T as(Class<T> c) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//	}
	
}
