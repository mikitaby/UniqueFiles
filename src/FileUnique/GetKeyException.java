package FileUnique;

@SuppressWarnings("serial")
class GetKeyException extends Throwable {
    public GetKeyException(Exception e) {
        super(e);
    }
}