# movieapp
Se usa arquitectura MVVM
• Películas y/o series categorizadas por Playing Now y Most Popular
• Detalle de película y/o serie.
• Visualización de videos en el detalle
Se usa corutinas, view binding y data binding
Se usa lenguaje español México para obtener titulo, trailers etc.
TODO si no se encuentra en español Mexico la sinopsis y trailers buscar en inglés
TODO usar Hilt para inyección de dependencias, Room para persistencia de datos, unit testing, 
TODO se agregó una interface IRepository que tiene los metodos de obtener movies, detail, el cual servirá para poder ser independiente del origen de datos Network, Room o Listas directas para testing
