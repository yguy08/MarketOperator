SELECT Base,Counter,Exchange,Date,Max(Close), Volume
FROM markets 
WHERE Base = Base 
AND Counter = Counter 
AND Date > 1503000090 
GROUP BY Base,Counter,Exchange,Date 
ORDER BY Counter ASC