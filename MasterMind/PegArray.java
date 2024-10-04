/**
 *  This class creates and manages one array of pegs from the game MasterMind.
 *
 *  @author	Riley Chow
 *  @since	September 27th, 2024
*/

public class PegArray {

	// array of pegs
	private Peg [] pegs;

	// the number of exact and partial matches for this array
	// as matched against the master.
	// Precondition: these values are valid after getExactMatches() and getPartialMatches()
	//				are called
	private int exactMatches, partialMatches;
		
	/**
	 *	Constructor
	 *	@param numPegs	number of pegs in the array
	 */
	public PegArray(int numPegs) {
		exactMatches = 0;
		partialMatches = 0;
		pegs = new Peg[numPegs];
		for (int i = 0; i < pegs.length; i++){
			pegs[i] = new Peg();
		}
	}
	/**
	 *	Return the peg object
	 *	@param n	The peg index into the array
	 *	@return		the peg object
	 */
	public Peg getPeg(int n) { return pegs[n]; }
	
	/**
	 *  Finds exact matches between master (key) peg array and this peg array
	 *	Postcondition: field exactMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of exact matches
	 */
	public int getExactMatches(PegArray master) { 
		for(int i = 0; i < pegs.length; i++){
			if(master.getPeg(i).getLetter() == pegs[i].getLetter()) exactMatches++;
		}
		return exactMatches;
		}
	
	/**
	 *  Find partial matches between master (key) peg array and this peg array
	 *	Postcondition: field partialMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of partial matches
	 */
	public int getPartialMatches(PegArray master) {
		int index = 0;
		char [] userPegs = new char[pegs.length];
		char [] masterPegs = new char[pegs.length];
		for(int i = 0; i < pegs.length; i++){
			userPegs[i] = pegs[i].getLetter();
			masterPegs[i] = master.getPeg(i).getLetter();
		}
		for(int i = 0; i < pegs.length; i++){
			for(int j = 0; j < pegs.length; j++){
				if(userPegs[j] == masterPegs[i] && i != j){
					partialMatches++;
					j+=4;
				}
			}
		}
		partialMatches -= exactMatches;
		if(exactMatches == pegs.length) partialMatches = 0;
		return partialMatches;
		}
	
	// Accessor methods
	// Precondition: getExactMatches() and getPartialMatches() must be called first
	public int getExact() { return exactMatches; }
	public int getPartial() { return partialMatches; }
}
