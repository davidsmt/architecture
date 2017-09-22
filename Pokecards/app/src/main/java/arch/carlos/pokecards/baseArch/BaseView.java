package arch.carlos.pokecards.baseArch;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}