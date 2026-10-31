import java.lang.String;
import org.junit.jupiter.api.Test;
class ContractTest {@Test void declaredRepresentationRoundsAfterTaxAndKeepsState(){MoneyProbe.check(MoneyCases.AFTER_TAX,MoneyCases.RATE,true);MoneyProbe.check(new String[]{},"1",true);MoneyProbe.check(MoneyCases.MIDPOINT,MoneyCases.MID_RATE,true);}}
