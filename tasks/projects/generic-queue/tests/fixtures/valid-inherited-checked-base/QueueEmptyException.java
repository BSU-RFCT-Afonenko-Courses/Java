package chosen.deep;
public class QueueEmptyException extends CheckedBase {private QueueEmptyException(String code,long number){super(code+number);}static QueueEmptyException factory(){return new QueueEmptyException("empty",17);}}
