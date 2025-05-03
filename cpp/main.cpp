#include <QApplication>
#include <QWidget>
#include <QPainter>
#include <QMouseEvent>
#include <QPixmap>
#include <vector>

class HoverWidget : public QWidget {
    Q_OBJECT

public:
    HoverWidget(QWidget *parent = nullptr)
        : QWidget(parent),
          background_(":/images/background.jpg"),
          overlays_({
            QPixmap(":/images/overlay1.png"),
            QPixmap(":/images/overlay2.png"),
            QPixmap(":/images/overlay3.png"),
            QPixmap(":/images/overlay4.png"),
            QPixmap(":/images/overlay5.png")
          }),
          spots_({
            QRect( 50,  60, 100, 80),
            QRect(200,  50, 120, 90),
            QRect(370,  70,  80, 80),
            QRect( 80, 200, 150,100),
            QRect(300, 220, 100,120)
          }),
          hoverIndex_(-1)
    {
        // so we get mouseMoveEvent even when no button is pressed
        setMouseTracking(true);
        // size the window to the background’s size
        setFixedSize(background_.size());
    }

protected:
    void paintEvent(QPaintEvent*) override {
        QPainter p(this);
        // draw background
        p.drawPixmap(rect(), background_);
        // draw overlay if hovering
        if (hoverIndex_ >= 0 && hoverIndex_ < overlays_.size()) {
            // scale overlay to the spot’s rect
            p.drawPixmap(spots_[hoverIndex_], overlays_[hoverIndex_]);
        }
    }

    void mouseMoveEvent(QMouseEvent *event) override {
        int newIndex = -1;
        QPoint pt = event->pos();
        for (int i = 0; i < spots_.size(); ++i) {
            if (spots_[i].contains(pt)) {
                newIndex = i;
                break;
            }
        }
        if (newIndex != hoverIndex_) {
            hoverIndex_ = newIndex;
            update();  // trigger repaint
        }
    }

private:
    QPixmap   background_;
    std::vector<QPixmap> overlays_;
    std::vector<QRect>   spots_;
    int       hoverIndex_;
};

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);
    HoverWidget w;
    w.show();
    return app.exec();
}

#include "main.moc"
// Note: The images used in this code should be placed in the appropriate resource file.