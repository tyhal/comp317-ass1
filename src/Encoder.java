import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.TreeMap;

public class Encoder
{
    private class Trie
    {
        public final long phrase_num = ++phrase_count; // Sets phrase_num and increments phrase count on construction
        public TreeMap< Character, Trie > trie = new TreeMap< Character, Trie >();
    };

    private long phrase_count = 0; // 0 is RESET phrase number
    private Trie dict = new Trie(), dict_curr = dict;

    public static void main( String[] args ) throws IOException
    {
        final BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
        final BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out, "UTF-8" ) );
        final int dict_limit = 1 << Integer.parseInt( args[ 0 ] ); // 2 ^ max_num_bits
        Encoder encoder = new Encoder(); // Create encoder with empty dictionary

        for( int next_c, c = br.read(); c != -1; c = next_c )
        {
            final boolean last = ( ( next_c = br.read() ) == -1 );

            if( encoder.phrase_count == dict_limit ) // Dictionary full
            {
                bw.write( ( encoder.phrase_count = 0 /* RESET phrase # */ ) + "," + Integer.toHexString( c ) + "\n" );
                encoder = new Encoder(); // Reset dictionary
            }
            else if( encoder.dict_curr.trie.containsKey( (char)c ) ) // Phrase in dictionary
            {
                encoder.dict_curr = encoder.dict_curr.trie.get( (char)c ); // Traverse Trie
                if( last )
                {
                    bw.write( encoder.dict_curr.phrase_num + "\n" );
                }
            }
            else
            {
                encoder.dict_curr.trie.put( (char)c, encoder.new Trie() ); // Add to dictionary
                bw.write( encoder.dict_curr.phrase_num + "," + Integer.toHexString( c ) + "\n" );
                encoder.dict_curr = encoder.dict; // Reset head back to start of Trie
            }
        }
        br.close();
        bw.close();
    }
}
