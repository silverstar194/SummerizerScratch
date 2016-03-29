

/**
 * The Class Config. Holds constants for program
 */
public class Config {

	/** Ratio for compress (default ratio 5) */
	public static int ratio = 5;

	/** Max ratio for compression */
	public static int maxCompressRatio = 10;

	/** Minimum ratio for compression */
	public static int minCompressRatio = 3;

	/** Minimum length for selected sentences in chars. */
	public static final int minLength = 8;

	/** Maximum length of a sentence in chars */
	public static final double maxLength = 350;

	/** Weight of keywords locally. */
	public static final double wordFreWeightLocal = .8;
	
	/** Weight of keywords globally. */
	public static final double wordFreWeightGlobal = 4;

	/** Weight of position of sentences. */
	public static final double posWeight = .1;

	/** Minimum amount for keywords to count. */
	public static final double minOccurences = 2;

	/** Minimum length for keywords*/
	public static final double minKeyWordLength = 3;
	
	/** Top Sentences (in percent) for Highlighting*/
	public static final double topPercent = 10;

}
