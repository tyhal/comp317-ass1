import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class Decoder {
    private ArrayList< Map.Entry< Long, Character > > dict = new ArrayList< Map.Entry< Long, Character > >();
    public void decode( long dict_idx, char prefix, boolean has_mismatch ) throws IOException {
        if( dict_idx >= 0 ) { // Phrase exists in dictionary
            Map.Entry< Long, Character > pair = dict.get( ( int )dict_idx );
            decode( pair.getKey() - 2, pair.getValue(), true ); // Traverse to root 1st then build up phrase from there
        }
        if( has_mismatch ) {
            System.out.print( prefix );
        }
    }
    public static void main( String[] args ) throws IOException {
        final BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
    	Decoder decoder = new Decoder();
        for( String line = br.readLine(); line != null; line = br.readLine() ) {
            String[] tokens = line.split( ",", 2 ); // Limit to 2 for case where value is ','
            long phrase_num = Long.parseLong( tokens[ 0 ] );
            char prefix = tokens.length == 2 ? (char)Integer.parseInt( tokens[ 1 ], 16 ) : '\0' /*can be anything*/;
            if( tokens.length == 2 && phrase_num != 0 ) { // False when last pair doesn't have mismatch
                decoder.dict.add( new AbstractMap.SimpleEntry< Long, Character >( phrase_num, prefix ) );
            } else if( phrase_num == 0 /* 0 is the RESET phrase number */ ) {
                decoder.dict.clear();
            }
            decoder.decode( phrase_num - 2, prefix, tokens.length == 2 );
        }
        br.close();
    }
}
