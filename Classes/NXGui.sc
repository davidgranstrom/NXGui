NXGui {
    var win, grid;
    var unitCounter;

    *new {|name|
        ^super.new.init(name);
    }

    init {|name|
        var size, winSize, x, y;

        unitCounter = 0;

        win = Window(name ? "NXGui");
        grid = GridLayout();

        win.view.background_(Color.grey(0.15));
        win.view.layout_(grid);
    }

    add {|unit|
        grid.add(unit.container, unitCounter mod: 3, unitCounter div: 3);
        unitCounter = unitCounter + 1;
    }

    front {
        this.resize;
        ^win.front;
    }

    resize {
        var winSize = win.minSizeHint;
        var x = (Window.availableBounds.width * 0.5) - (winSize.width * 0.5);
        var y = (Window.availableBounds.height * 0.5) - (0.5 * winSize.height);

        win.bounds = Rect(x, y, winSize.width, winSize.height);
    }
}

NXGuiUnit {
    var <container, grid;
    var parameters, paramCounter, gridMargins, gridSpacing;
    var font, labelFont;

    *new {|name|
        ^super.new.init(name);
    }

    init {|name|
        var label;

        font = Font("Menlo", 10);
        labelFont = Font("Menlo", 14);

        parameters = (); // store all parameters
        paramCounter = 0;
        gridMargins = 3;
        gridSpacing = 3;

        container = View();

        grid = GridLayout();
        grid.margins_(gridMargins);
        grid.spacing_(gridSpacing);

        label = StaticText().font_(labelFont).string_(name);
        label.background_(Color.cyan(0.25));
        label.maxHeight_(label.minSizeHint.height);

        grid.addSpanning(label, 0, 0, 1, 5);

        container.layout_(grid);
        container.background_(Color.grey(0.35));
    }

    makeParameter {|name, options|
        var layout, view = View();
        var label = StaticText().font_(font).align_(\right).string_(name);
        var box = NumberBox();

        box.fixedSize_(Size(70,25));

        // set defaults
        options[\value] !? { box.value = options[\value] };
        options[\action] !? { box.action = options[\action] };

        parameters[name] !? {
            "A parameter with name % already exists and will be overwritten".format(name).warn;
        };
        parameters[name] = (box: box, view: view); // store the number box

        layout = HLayout(label, box).spacing_(gridSpacing).margins_(gridMargins);
        view.layout_(layout);

        ^view;
    }

    add {|name, options|
        var parameter = this.makeParameter(name, options ? ());

        grid.add(parameter, 1 + (paramCounter mod: 3), paramCounter div: 3);
        paramCounter = paramCounter + 1;

        this.resizeContainer;
    }

    resizeContainer {
        container.bounds = container.bounds.size_(container.sizeHint);
    }

    update {|name, val|
        var parameter = parameters[name].box;

        parameter !? {
            parameter.value = val;
        }
    }

    remove {|name|
        var parameter = parameters[name].view;

        parameter !? {
            parameter.remove;
            parameters[name] = nil;
        }
    }
}
