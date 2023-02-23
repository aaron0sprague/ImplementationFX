package userinterface;

import impresario.IModel;

public class ViewFactory {

    public static View createView(String viewName, IModel model) {
        switch (viewName) {
            case "LibrarianView":
                return new LibrarianView(model);
            case "BookView":
                return new BookView(model);
            case "BookCollectionView":
                return new BookCollectionView(model);
            case "BookSearchView":
                return new BookSearchView(model);
            default:
                return null;
        }
    }
}
