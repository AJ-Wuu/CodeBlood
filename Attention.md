# Avoid Number Overflow
Original (might cause overflow): ```int mid = (start + end) / 2);```  
Improvement 1: ```int mid = start + (end - start)/2);```  
Improvement 2: ```int mid = (start + end) >>> 1; //">>>" is logical shift, so it does not extend the signed part```  
