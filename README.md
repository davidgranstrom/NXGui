NXGui
=====

A minimalistic GUI class.
This is a work in progress, breaking changes might still occur.

Roadmap
-------

* Enable to update a parameter and trigger its action (using valueAction).
* Be able to style GUI (colors, fonts).

Usage
-----

###Define a unit

A unit describes a set of parameters which will be available to the user.
A parameter is added using the add message (see below), it requires a label and an optional Dictionary with defaults.
The key 'value' will be the UI's initial value.
The key 'action' is an optional callback to be performed when the value changes.

    (
    m = NXGuiUnit('main');
    m.add('hpf', (value: 20));
    m.add('lpf', (value: 20000, action: {|param| param.value.postln }));
    )


###Creating a GUI

Units can be added to a NXGui and will be placed inside it using a GridLayout.

    (
    g = NXGui('myGui');
    g.add(m);
    g.front;
    )

    // update the 'lpf' parameter
    m.update('lpf', 7500);

