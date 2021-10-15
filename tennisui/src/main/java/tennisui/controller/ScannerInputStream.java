package tennisui.controller;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ScannerInputStream extends FilterInputStream {
	
    public ScannerInputStream(InputStream in) {
		super(in);
	}

	@Override
    public void close() throws IOException {
        // to avoid closing System.in (to fake the close)
		//  as we are using multiple successive scanner calls
		// good idea & pattern ref. in https://stackoverflow.com/questions/14962082/close-scanner-without-closing-system-in
    }

}
