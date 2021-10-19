package in.mcxiv.jpsd.exceptions;

public interface ExceptionThrower<E extends Exception> {
    void throwIt() throws E;
}
