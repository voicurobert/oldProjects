using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using Microsoft.Phone.Controls;
using Microsoft.Devices.Radio;
using System.Windows.Threading;

namespace Radio {
    public partial class MainPage : PhoneApplicationPage {
        double frequency;
        DateTime? lastDigitButtonTap;
        // Two theme-specific custom brushes
        public Brush CalculatorMainBrush { get; set; }
        public Brush CalculatorSecondaryBrush { get; set; }
        // Constructor
        public MainPage() {
            InitializeComponent();
            // Ensure the radio is on
            StartRadio();
            // Work around having no radio events by polling
            DispatcherTimer timer = new DispatcherTimer {
                Interval = TimeSpan.FromSeconds(2) };

            timer.Tick += delegate(object sender, EventArgs e) {
                // Update the signal strength every two seconds
                this.SignalStrengthTextBlock.Text = "signal strength: " +(FMRadio.Instance.SignalStrength * 100).ToString("##0");
            };

            timer.Start();
// A single handler for all calculator button taps
this.ButtonPanel.AddHandler(Button.MouseLeftButtonUpEvent,
new MouseButtonEventHandler(CalculatorButton_MouseLeftButtonUp),
true /* handledEventsToo */);
// Handlers to ensure that the backspace button’s vector content changes
// color appropriately when the button is pressed
this.BackspaceButton.AddHandler(Button.MouseLeftButtonDownEvent,
new MouseButtonEventHandler(BackspaceButton_MouseLeftButtonDown),
true /* handledEventsToo */);
this.BackspaceButton.AddHandler(Button.MouseLeftButtonUpEvent,
new MouseButtonEventHandler(BackspaceButton_MouseLeftButtonUp),
true /* handledEventsToo */);
this.BackspaceButton.MouseMove += BackspaceButton_MouseMove;
// Set the colors of the two custom brushes based on whether
// we’re in the light theme or dark theme
if ((Visibility)Application.Current.Resources["PhoneLightThemeVisibility"]
== Visibility.Visible)
{
this.CalculatorMainBrush = new SolidColorBrush(
Color.FromArgb(0xFF, 0xEF, 0xEF, 0xEF));
this.CalculatorSecondaryBrush = new SolidColorBrush(
Color.FromArgb(0xFF, 0xDE, 0xDF, 0xDE));
}
else
{
this.CalculatorMainBrush = new SolidColorBrush(
Color.FromArgb(0xFF, 0x18, 0x1C, 0x18));
this.CalculatorSecondaryBrush = new SolidColorBrush(
Color.FromArgb(0xFF, 0x31, 0x30, 0x31));
}
// Grab the current frequency from the device’s radio
this.frequency = FMRadio.Instance.Frequency;
UpdateFrequencyDisplay();
}
void StartRadio()
{
try
{
// This would throw a RadioDisabledException if the app weren’t given
// the ID_CAP_MEDIALIB capability, but we’re worried instead about an
// UnauthorizedAccessException thrown when the phone is connected to Zune
FMRadio.Instance.PowerMode = RadioPowerMode.On;
}
catch
{
// Show a message explaining the limitation while connected to Zune
MessageBox.Show("Be sure that your phone is disconnected from your " +
"computer.", "Cannot turn radio on", MessageBoxButton.OK);
return;
}
if (FMRadio.Instance.SignalStrength == 0)
{
// Show a message similar to the built-in radio app
MessageBox.Show("This phone uses your headphones as an FM radio " +
"antenna. To listen to radio, connect your headphones.", "No antenna",
MessageBoxButton.OK);
}
}
void StopRadio()
{
try { FMRadio.Instance.PowerMode = RadioPowerMode.Off; }
catch {} // Ignore exception from being connected to Zune
}
// A single handler for all calculator button taps
        void CalculatorButton_MouseLeftButtonUp(object sender, MouseButtonEventArgs e)
{
// Although sender is the canvas, the OriginalSource is the tapped button
Button button = e.OriginalSource as Button;
if (button == null)
return;
string content = button.Content.ToString();
// Determine what to do based on the string content of the tapped button
double digit;
if (content == "power")
{
if (FMRadio.Instance.PowerMode == RadioPowerMode.On)
StopRadio();
else
StartRadio();
}
else if (double.TryParse(content, out digit)) // double so division works
{
// If there are already four digits (including the decimal place), or if
// the user hasn’t recently typed digits, clear the frequency first
if (this.frequency > 100 || this.lastDigitButtonTap == null ||
DateTime.Now - this.lastDigitButtonTap > TimeSpan.FromSeconds(3))
this.frequency = 0;
// Append the digit
this.frequency *= 10;
this.frequency += digit / 10;
this.lastDigitButtonTap = DateTime.Now;
}
else if (content == "C")
{
// Clear the frequency
this.frequency = 0;
}
else // The backspace button
{
// Chop off the last digit (the decimal place) with a cast
int temp = (int)this.frequency;
// Shift right by 1 place
this.frequency = (double)temp / 10;
}
            UpdateFrequencyDisplay();
}
void UpdateFrequencyDisplay()
{
try
{
this.FrequencyDisplay.Foreground =
Application.Current.Resources["PhoneAccentBrush"] as Brush;
// Update the display
this.FrequencyDisplay.Frequency = this.frequency;
// Update the radio
FMRadio.Instance.Frequency = this.frequency;
}
catch
{
if (FMRadio.Instance.PowerMode == RadioPowerMode.On)
{
// Caused by an invalid frequency value, which easily
// happens while typing a valid frequency
this.FrequencyDisplay.Foreground = new SolidColorBrush(Colors.Red);
}
}
}
// Change the color of the two paths inside the backspace button when pressed
void BackspaceButton_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
{
this.BackspaceXPath.Stroke =
Application.Current.Resources["PhoneBackgroundBrush"] as Brush;
this.BackspaceBorderPath.Stroke =
Application.Current.Resources["PhoneBackgroundBrush"] as Brush;
}
// Change the color of the two paths back when no longer pressed
void BackspaceButton_MouseLeftButtonUp(object sender, MouseEventArgs e)
{
this.BackspaceXPath.Stroke =
Application.Current.Resources["PhoneForegroundBrush"] as Brush;
this.BackspaceBorderPath.Stroke =
Application.Current.Resources["PhoneForegroundBrush"] as Brush;
}
        // Workaround for when the finger has not yet been released but the color
// needs to change back because the finger is no longer over the button
void BackspaceButton_MouseMove(object sender, MouseEventArgs e)
{
// this.BackspaceButton.IsMouseOver lies when it has captured the mouse!
// Use GetPosition instead:
Point relativePoint = e.GetPosition(this.BackspaceButton);
// We can get away with this simple check because
// the button is in the bottom-right corner
if (relativePoint.X < 0 || relativePoint.Y < 0)
BackspaceButton_MouseLeftButtonUp(null, null); // Not over the button
else
BackspaceButton_MouseLeftButtonDown(null, null); // Still over the button
}

        }
    }
