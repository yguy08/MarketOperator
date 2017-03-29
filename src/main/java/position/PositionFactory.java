package position;

import entry.Entry;

public class PositionFactory {
	
	public Position createPosition(Entry entry){
		if(entry == null){
			return null;
		}
		
		if(entry.getDirection().equalsIgnoreCase("Long")){
			return new LongPosition(entry);
		}else if(entry.getDirection().equalsIgnoreCase("Short")){
			return new ShortPosition(entry);
		}
		
		return null;
	}

}
