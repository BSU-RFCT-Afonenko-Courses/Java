package chosen.deep;
public class QueueFullException extends CheckedBase {private QueueFullException(String code,long number){super(code+number);}static QueueFullException factory(){return new QueueFullException("full",17);}}
